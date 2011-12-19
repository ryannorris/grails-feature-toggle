<html>
<head>
<title>Site Information</title>


</head>
<body>

	<table id="features">
		<tbody>
			<g:each in="${features.keySet()}" var="feature">
				<g:set var="enabled"
					value="${featureEnabled(feature: feature)}"></g:set>
				<tr>
					<td>
						${feature}
					</td>
					
					<td>
						${features[feature].description}
					</td>
					<td><g:if test="${enabled == 'true'}">
							<b>Enabled</b>
						</g:if> <g:else>
							<a
								href="${createLink(controller: 'featureToggle', action: 'enable', params: [ feature: feature ])}">Enable</a>
						</g:else></td>
					<td><g:if test="${enabled == 'false'}">
							<b>Disabled</b>
						</g:if> <g:else>
							<a
								href="${createLink(controller: 'featureToggle', action: 'disable', params: [ feature: feature ])}">Disable</a>
						</g:else></td>
				</tr>
			</g:each>
		</tbody>
	</table>

	<div>
		<a
			href="${createLink(controller: 'featureToggle', action: 'download')}">Download
			Current Config</a>
	</div>

</body>
</html>