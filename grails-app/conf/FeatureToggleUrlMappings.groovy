class FeatureToggleUrlMappings {

	static mappings = {
		 name featureList: "/features" {
			controller = "featureToggle"
			action = "list"
		}
		
		name enableFeature: "/feature/enable" {
			controller = "featureToggle"
			action = "enable"
		}
		
		name disableFeature: "/feature/disable"{
			controller = "featureToggle"
			action = "disable"
		}
		
		
		name downloadConfig: "/feature/config"{
			controller = "featureToggle"
			action = "download"
		}

		name sampleList: "/sample" {
			controller = "sample"
		}
	}
}
