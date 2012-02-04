package grails.plugin.featuretoggle

//import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ToggleTagLib {
	def featureToggleService

	/**
	 * Toggles whether a feature is enabled in the user interface, as configured in Config.groovy
	 * under swapfish.features[:]
	 *
	 * @attr feature REQUIRED the name of the feature
	 */
	def toggle = { attrs, body ->

		def feature = attrs.feature

		if(featureToggleService.isFeatureEnabled(feature)) {
			out << body()
		}
	}

	def featureEnabled = { attrs ->
		out << featureToggleService.isFeatureEnabled(attrs.feature)
	}
}
