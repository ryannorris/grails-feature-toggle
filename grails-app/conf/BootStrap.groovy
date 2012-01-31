import org.codehaus.groovy.grails.commons.ApplicationAttributes
import grails.plugin.featuretoggle.annotations.Feature

import java.lang.reflect.Field

class BootStrap {

	def grailsApplication
	
	def init = { servletContext ->
		def controlledActions = [:]
		
		grailsApplication.controllerClasses.each{ controller ->
			def annotation = controller.getClazz().getAnnotation(Feature)
			def isControllerFeature = annotation != null

			controller.reference.propertyDescriptors.each{ pd -> 
				def closure = controller.getPropertyOrStaticPropertyOrFieldValue(pd.name, Closure)
				
				if(closure) {
					if(pd.name != 'beforeInterceptor' && pd.name != 'afterInterceptor') {
						Field curField = controller.getClazz().getDeclaredField(pd.name)
						
						def fieldAnnotation = curField.getAnnotation(Feature)
						
						
						if(fieldAnnotation != null) {
							Result result = new Result(name:fieldAnnotation.name(), resultStatus:fieldAnnotation.responseStatus(), resultRedirect:fieldAnnotation.responseRedirect())
							controlledActions.put(controller.name.toLowerCase() + '.' + pd.name.toLowerCase(), result)
						} else if(isControllerFeature) {
							Result result = new Result(name:annotation.name(), resultStatus:annotation.responseStatus(), resultRedirect:annotation.responseRedirect())
							controlledActions.put(controller.name.toLowerCase() + '.' + pd.name.toLowerCase(), result)
						}
					}
				}
			}
		}
	
		servletContext["controlledActions"] = controlledActions
	}
}