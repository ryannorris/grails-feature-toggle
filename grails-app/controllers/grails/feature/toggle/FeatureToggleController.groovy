package grails.feature.toggle

import grails.plugin.featuretoggle.FeatureToggleService

class FeatureToggleController {

	FeatureToggleService featureToggleService

	def list = {
		def toggles = featureToggleService.allFeatures()

		withFormat {
			json {
				render(contentType: "application/json") {
					features {
						toggles.keySet().each { featureName ->
							feature(name: featureName, enabled: featureToggleService.isFeatureEnabled(featureName), description: features[featureName].description)
						}
					}
				}
			}

			html {
				render(view: 'index', model: [ features : toggles ])
			}
		}
	}

	def disable = {
		featureToggleService.allFeatures()."${params.feature}".enabled = false
		log.debug featureToggleService.allFeatures()."${params.feature}".enabled
		redirect(action: "list")
	}

	def enable = {
		featureToggleService.disableDefaultOverride()
		featureToggleService.allFeatures()."${params.feature}".enabled = true
		log.debug featureToggleService.allFeatures()."${params.feature}".enabled
		redirect(action: "list")
	}

	def download = {
		render(text: featureToggleService.allFeatures())
	}
}
