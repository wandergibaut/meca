/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP                  *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     W. Gibaut and R. R. Gudwin                                              * 
 *                                                                             *
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.system1.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import java.util.ArrayList;

/**
 *
 * @author W. Gibaut
 */
public abstract class AdaptationCodelet extends Codelet{
    //private Memory appraisalMO;
    //private Memory naturalizationMO;
    
    protected String id;
    protected ArrayList<String> perceptualCodeletsIds; 
    protected ArrayList<String> motorCodeletsIds;
    protected String plannerCodeletId;
    
    
    public AdaptationCodelet(String id, ArrayList<String> perceptualCodeletsIds, ArrayList<String> motorCodeletsIds, String plannerCodeletId){
        this.id = id;
        this.perceptualCodeletsIds = perceptualCodeletsIds;
        this.motorCodeletsIds = motorCodeletsIds;
        this.plannerCodeletId = plannerCodeletId;
    }
    
    
    @Override
    public void accessMemoryObjects() {
        //this.appraisalMO = this.getInput("OUTPUT_APPRAISAL_MEMORY");
        //this.naturalizationMO = this.getOutput("NATURALIZATION");
    }

    @Override
    public void proc() {
        //a ideia eh compilar em tempo de  execucao behaviors q possam substituir os processos mais complexos
    }
        
    @Override
    public void calculateActivation() {
    }
    
    
    public abstract void insertAndLink(ReactiveBehavioralCodelet bCodelet);
    
    /**
	 * Returns the id of the Planner Codelet whose outputs will be read by this
	 * Reactive Behavioral Codelet.
	 * 
	 * @return the plannerCodeletId
	 */
	public String getPlannerCodeletId() {
		return plannerCodeletId;
	}

	/**
	 * Sets the id of the Planner Codelet whose outputs will be read by this
	 * Reactive Behavioral Codelet.
	 * 
	 * @param plannerCodeletId
	 *            the plannerCodeletId to set
	 */
	public void setPlannerCodeletId(String plannerCodeletId) {
		this.plannerCodeletId = plannerCodeletId;
	}

	/**
	 * Returns the list of the Perceptual Codelet's ids whose outputs will be
	 * read by this Reactive Behavioral Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of the Perceptual Codelet's ids whose outputs will be read
	 * by this Reactive Behavioral Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}

	/**
	 * Returns the id of this Reactive Behavioral Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Reactive Behavioral Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the Motor Codelet which will read the outputs of this
	 * Reactive Behavioral Codelet.
	 * 
	 * @return the motorCodeletIds
	 */
	public ArrayList<String> getMotorCodeletIds() {
		return motorCodeletsIds;
	}

	/**
	 * Sets the id of the Motor Codelet which will read the outputs of this
	 * Reactive Behavioral Codelet.
	 * 
	 * @param motorCodeletIds
	 *            the motorCodeletId to set
	 */
	public void setMotorCodeletId(ArrayList<String> motorCodeletIds) {
		this.motorCodeletsIds = motorCodeletIds;
	}
}
