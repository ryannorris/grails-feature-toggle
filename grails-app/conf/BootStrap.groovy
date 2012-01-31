import org.codehaus.groovy.grails.commons.ApplicationAttributes
import grails.plugin.featuretoggle.annotations.Feature

import java.lang.reflect.Field

class BootStrap {

	def grailsApplication
	
	def init = { servletContext ->
		def controlledActions = [:]
		
		grailsApplication.controllerClasses.each{ controller ->
			def annotation = controller.getClazz().getAnnotation(Feature)

			parseActionClosures(controller, annotation, controlledActions)
			parseActionMethods(controller, annotation, controlledActions)
		}
	
		servletContext["controlledActions"] = controlledActions
	}
	
	def parseActionClosures = { controller, annotation, controlledActions ->
		def isControllerFeature = annotation != null
		
		controller.reference.propertyDescriptors.each{ pd -> 
			def closure = controller.getPropertyOrStaticPropertyOrFieldValue(pd.name, Closure)
		
			if(closure) {
				if(pd.name != 'beforeInterceptor' && pd.name != 'afterInterceptor') {
					Field curField = controller.getClazz().getDeclaredField(pd.name)
					def key = controller.name.toLowerCase() + '.' + pd.name.toLowerCase()
				
					def fieldAnnotation = curField.getAnnotation Feature
				
					if(fieldAnnotation != null) {
						Result result = new Result(name:fieldAnnotation.name(), resultStatus:fieldAnnotation.responseStatus(), resultRedirect:fieldAnnotation.responseRedirect())
						controlledActions.put(key, result)
					} else if(isControllerFeature) {
						Result result = new Result(name:annotation.name(), resultStatus:annotation.responseStatus(), resultRedirect:annotation.responseRedirect())
						controlledActions.put(key, result)
					}
				}
			}
		}
	}

	def parseActionMethods = { controller, annotation, controlledActions ->
		def isControllerFeature = annotation != null

		controller.clazz.declaredMethods.each{ method ->
		
			if(method.name.indexOf('$') < 0) {
				def methodAnnotation = method.getAnnotation Feature
				def key = controller.name.toLowerCase() + '.' + method.name.toLowerCase()

				if(controlledActions[key] == null) {
					if(methodAnnotation != null) {
						Result result = new Result(name:methodAnnotation.name(), resultStatus:methodAnnotation.responseStatus(), resultRedirect:methodAnnotation.responseRedirect())
						controlledActions.put(key, result)
					} else if(isControllerFeature) {
						Result result = new Result(name:annotation.name(), resultStatus:annotation.responseStatus(), resultRedirect:annotation.responseRedirect())
						controlledActions.put(key, result)
					}
				}
			}
		}
	}
}