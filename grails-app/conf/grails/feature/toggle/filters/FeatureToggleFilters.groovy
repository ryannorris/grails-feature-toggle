package grails.feature.toggle.filters
import grails.plugin.featuretoggle.FeatureToggleService;

class FeatureToggleFilters {

	FeatureToggleService featureToggleService

	def filters = {
		allControllers(controller:'*', action:'*') {
			before = { 
				def controllers = request.servletContext['controlledActions']
				
				def action = controllers[controllerName.toLowerCase() + '.' + actionName.toLowerCase()] 
				
				if(action != null && !featureToggleService.isFeatureEnabled(action.name)) {
					if(action.resultRedirect.size() > 0) {
						redirect(uri: action.resultRedirect)
					} else {
						render(status: action.resultStatus )
					}
				}
			}
		}
	}
}