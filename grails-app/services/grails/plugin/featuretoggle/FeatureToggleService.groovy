package grails.plugin.featuretoggle

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class FeatureToggleService {

    static transactional = false

    def allFeatures() {
		CH.config.features.findAll { it.key != "disableAll" }
    }
	
	def featuresDisabled() {
		return CH.config.features?.disableAll == true && CH.config.features?.disableAll != false
	}
	
	def disableDefaultOverride() {
		CH.config.features?.disableAll = false
	}
	
	def isFeatureEnabled(def feature) {
		
		def isFeatureEnabled = true
		
		if(featuresDisabled()) {
			log.debug("all features toggled off")
			return false
		}

		log.debug("checking to see if feature '${feature}' is enabled...")

		def featureConfig = allFeatures()?."${feature}"

		if(featureConfig) {
			
			isFeatureEnabled = (featureConfig.enabled.getClass() == groovy.util.ConfigObject) ? true : featureConfig.enabled

			log.debug("feature is ${isFeatureEnabled ? 'enabled' : 'disabled'} - (${isFeatureEnabled})")
		}
		
		isFeatureEnabled
	}
}
