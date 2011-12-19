package grails.feature.toggle

class SampleController {

    def index = { 
		withFeature("sample") { ->
			log.debug("the feature must be enabled")
		}	
	}
}
