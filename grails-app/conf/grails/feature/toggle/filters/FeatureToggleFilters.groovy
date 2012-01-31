package grails.feature.toggle.filters
import grails.plugin.featuretoggle.FeatureToggleService;
import grails.plugin.featuretoggle.annotations.FeatureToggle;

class FeatureToggleFilters {

	FeatureToggleService featureToggleService

	def filters = {
		allControllers(controller:'*', action:'*') {
			before = {
				def artefact = grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName)

				def curController = applicationContext.getBean(artefact.clazz.name)

				def annotation = curController.class.getAnnotation(FeatureToggle)

				if(annotation != null && !featureToggleService.isFeatureEnabled(annotation.feature())) {
					render(status: annotation.responseStatus())
					return
				}
			}
		}
	}
}