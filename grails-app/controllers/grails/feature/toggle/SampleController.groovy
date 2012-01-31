package grails.feature.toggle

import grails.plugin.featuretoggle.annotations.FeatureToggle;

@FeatureToggle(feature='sample')
class SampleController {

	def index = {
		withFeature("sample") { ->
			log.debug("the feature must be enabled")
		}
	}
}