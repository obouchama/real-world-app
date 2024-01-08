package com.liferay.training.gradebook.service.impl;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
		scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
		id = "com.liferay.samples.category.based.search.filter.config.CustomFilterConfiguration",
		name="Paramètres de la recherche des actus"
)
public interface CustomFilterConfiguration {

	@Meta.AD(
			deflt = "38257", required = false, description = "DDM Structure key pour toutes les Actus"
	)
	String actuDDMStructure();

}

