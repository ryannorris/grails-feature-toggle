package grails.plugin.featuretoggle

//import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class FeatureToggleService {

	def grailsApplication

	static transactional = false

	def allFeatures() {
		grailsApplication.config.features.findAll { it.key != "disableAll" }
	}

	def featuresDisabled() {
		return grailsApplication.config.features?.disableAll == true && grailsApplication.config.features?.disableAll != false
	}

	def disableDefaultOverride() {
		grailsApplication.config.features?.disableAll = false
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
