package grails.feature.toggle

import grails.plugin.featuretoggle.annotations.Feature;

@Feature(name='sample')
class SampleController {

	@Feature(name='featuredSample')
	def index = {
		withFeature("sample") { ->
			log.debug("the feature must be enabled")
		}
	}
}