/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.patientsummary.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientsummary.PatientSummary;
import org.openmrs.module.patientsummary.PatientSummaryReportDefinition;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.service.ReportService;

/**
 * The core implementation of {@link PatientSummaryService}.
 */
public class PatientSummaryServiceImpl extends BaseOpenmrsService implements PatientSummaryService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @see PatientSummaryService#getPatientSummaryReportDefinition(Integer)
	 */
	@Override
	public PatientSummaryReportDefinition getPatientSummaryReportDefinition(Integer id) {
		return (PatientSummaryReportDefinition)getReportDefinitionService().getDefinition(id);
	}
	
	/**
	 * @see PatientSummaryService#getPatientSummaryReportDefinitionByUuid(String)
	 */
	@Override
	public PatientSummaryReportDefinition getPatientSummaryReportDefinitionByUuid(String uuid) {
		return (PatientSummaryReportDefinition)getReportDefinitionService().getDefinitionByUuid(uuid);
	}
	
	/**
	 * @see PatientSummaryService#getAllPatientSummaryDefinitions(boolean)
	 */
	@Override
	public List<PatientSummaryReportDefinition> getAllPatientSummaryReportDefinitions(boolean includeRetired) {
		List<PatientSummaryReportDefinition> l = new ArrayList<PatientSummaryReportDefinition>();
		for (ReportDefinition d : getReportDefinitionService().getAllDefinitions(includeRetired)) {
			if (d instanceof PatientSummaryReportDefinition) {
				l.add((PatientSummaryReportDefinition)d);
			}
		}
		return l;
	}

	/**
	 * @see PatientSummaryService#getPatientSummary(Integer)
	 */
	@Override
	public PatientSummary getPatientSummary(Integer id) {
		ReportDesign d = getReportService().getReportDesign(id);
		return new PatientSummary(d);
	}

	/**
	 * @see PatientSummaryService#getPatientSummaryByUuid(String)
	 */
	@Override
	public PatientSummary getPatientSummaryByUuid(String uuid) {
		ReportDesign d = getReportService().getReportDesignByUuid(uuid);
		return new PatientSummary(d);
	}

	/**
	 * @see PatientSummaryService#getAllPatientSummaries(boolean)
	 */
	@Override
	public List<PatientSummary> getAllPatientSummaries(boolean includeRetired) {
		List<PatientSummary> l = new ArrayList<PatientSummary>();
		for (ReportDesign d : getReportService().getAllReportDesigns(includeRetired)) {
			if (d.getReportDefinition() instanceof PatientSummaryReportDefinition) {
				l.add(new PatientSummary(d));
			}
		}
		return l;
	}
	
	/**
	 * @return the underlying ReportService used to manage the patient summary report definitions
	 */
	protected ReportDefinitionService getReportDefinitionService() {
		return Context.getService(ReportDefinitionService.class);
	}
	
	/**
	 * @return the underlying ReportService used to manage the patient summaries
	 */
	protected ReportService getReportService() {
		return Context.getService(ReportService.class);
	}
}