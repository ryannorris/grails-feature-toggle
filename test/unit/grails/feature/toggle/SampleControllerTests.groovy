package grails.feature.toggle

import grails.feature.toggle.filters.FeatureToggleFilters
import grails.plugin.featuretoggle.FeatureToggleService
import grails.test.mixin.*


/**
 * This test is actually used to test the FeatureToggleFilters implementation by
 * testing the SampleController with that filter.
 *
 * @author mminella
 */
@TestFor(SampleController)
@Mock(FeatureToggleFilters)
class SampleControllerTests {

	def featureService

	void testFeatureIsDisabled() {
		def featureService = mockFor(FeatureToggleService)
		featureService.demand.isFeatureEnabled {String feature -> return false}
		
		withFilters(action: 'index') {
			controller.index()
		}

		assert response.status == 404
	}
}
