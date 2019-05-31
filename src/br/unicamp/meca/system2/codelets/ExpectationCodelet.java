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
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
//import br.unicamp.cst.core.entities.MemoryObject;
//import br.unicamp.cst.representation.owrl.AbstractObject;
//import br.unicamp.cst.representation.owrl.Property;
//import java.util.HashMap;
//import java.util.List;
//import util.Episode;

/**
 *
 * @author W. Gibaut
 */
public abstract class ExpectationCodelet extends Codelet{
    //transformar em abstrato
    
    protected String id;
    
    private Memory expectedMapMemory;
    //private Memory actionPoolMemory;
    private Memory currentPerceptionMemory;
    //private Memory nextActionMO;
    
    private Memory weightsOutputMemory;
    private Memory weightsInputMemory;
    //private Memory encodedConfMemory;
    private Memory episodicRecallMemory;
    //public Memory syncWait;
    
    //public Memory expectMO;
    //public Memory currentStateMO;
    
    //private List<String> actions;
    
    /** We need a reference to the SelectionCodelet which will read the outputs of this ExpectationCodelet*/
	protected String selectionCodeletId;
    
    /** We need a reference to the LearningCodelet which will read the outputs of this ExpectationCodelet*/
	protected String learningCodeletId;
        
    /** We need a reference to the LearningCodelet which will read the outputs of this ExpectationCodelet*/
	protected String attentionCodeletId;
    
    /** We need a reference to the LearningCodelet which will read the outputs of this ExpectationCodelet*/
        protected String episodicRecallMemoryId;
   
    
    /**
	 * Creates a MECA Expectation Codelet.
	 * 
	 * @param id
	 *            the id of the Expectation Codelet. Must be unique.
         * @param selectionCodeletId
         * @param learningCodeletId
         * @param attentionCodeletId
	 */
	public ExpectationCodelet(String id, String selectionCodeletId, String learningCodeletId, String attentionCodeletId) {
		super();
		this.id = id;
                this.learningCodeletId = learningCodeletId;
                this.selectionCodeletId = selectionCodeletId;
                this.attentionCodeletId = attentionCodeletId;
		setName(id);
	}
    
    /*@Override
    public void accessMemoryObjects() {
        this.actionPoolMemory = this.getInput("ACTION_POOL");
        this.currentPerceptionMO = this.getInput("CURRENT_PERCEPTION");
        this.predictedPoolMO = this.getOutput("PREDICTED_POOL");
        this.nextActionMO = this.getInput("NEXT_ACTION");
        
        this.weightsMemory = this.getInput("WEIGHTS_RECORD");
        this.weightsInputMemory = this.getOutput("WEIGHTS_INPUT");
        
        this.encodedConfMemory = this.getOutput("ENCODED_CONFIGURATION");
        
        this.episodicRecallMemoryMO = this.getInput("EPISODIC_RECALL_MEMORY");
        this.syncWait = this.getInput("SYNC");
        
        this.expectMO = this.getOutput("EXPECTNET");
        this.currentStateMO = this.getOutput("CURRENT_STATE");
    }*/

    @Override
    public void proc() {

        
        //pegar inputs
        
        //processar
        
        //entregar outputs
        
        
    }
    
    /*public float[][] receiveColumn (float[][] first, float[] second, int pos){
        for (int i = 0; i < second.length; i++){
                first[i][pos] = second[i];
        }
        return first;
    }
    
    public double[][] receiveRowDouble (double[][] first, double[] second, int pos){
        for (int i = 0; i < second.length; i++){
                first[pos][i] = second[i];
        }
        return first;
    }
    */
    
    public Memory getCurrentPerceptionMemory(){
        return this.currentPerceptionMemory;
    }
    //public Memory getActionPoolMemory(){
    //    return this.actionPoolMemory;
    //}
    public Memory getExpectedMapMemory(){
        return this.expectedMapMemory;
    }
    
    public void setCurrentPerceptionMemory( Memory currentPerceptionMO){
        this.currentPerceptionMemory = currentPerceptionMO;
    }
    //public void setActionPoolMemory(Memory actionPoolMemory){
    //    this.actionPoolMemory = actionPoolMemory;
    //}
    public void setExpectedMapMemory(Memory expectedMapMemory){
        this.expectedMapMemory = expectedMapMemory;
    }
    
    public void setEpisodicRecallMemory(Memory episodicRecallMemoryMO){
        this.episodicRecallMemory = episodicRecallMemoryMO;
    }
    public Memory getEpisodicRecallMemory(){
        return this.episodicRecallMemory;
    }
    
    public Memory getOutputWeightsMemory(){
        return this.weightsOutputMemory;
    }
    public void setOutputWeightsMemory(Memory weightsMemory){
        this.weightsOutputMemory = weightsMemory;
    }
    
    public Memory getInputWeightsMemory(){
        return this.weightsInputMemory;
    }
    public void setInputWeightsMemory(Memory weightsInputMemory){
        this.weightsInputMemory = weightsInputMemory;
    }
    
    //public Memory getEncodedConfMemory(){
    //    return this.encodedConfMemory;
    //}
    //public void setEncodedConfMemory(Memory encodedConfMemory){
    //    this.encodedConfMemory = encodedConfMemory;
    //}
    
    //public Memory getNextActionMemory(){
    //    return this.nextActionMO;
    //}
    //public void setNextActionMemory(Memory nextActionMO){
   //     this.nextActionMO = nextActionMO;
    //}

    
    /**
	 * Returns the id of this Motor Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Motor Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
        
        /**
	 * @return the learningCodeletId
	 */
	public String getLearningCodeletId() {
		return learningCodeletId;
	}


	/**
	 * @param learningCodeletId the learningCodeletId to set
	 */
	public void setLearningCodeletId(String learningCodeletId) {
		this.learningCodeletId = learningCodeletId;
	}
        
        
        /**
	 * @return the selectionCodeletId
	 */
	public String getSelectionCodeletId() {
		return selectionCodeletId;
	}


	/**
	 * @param selectionCodeletId the selectionCodeletId to set
	 */
	public void setSelectionCodeletId(String selectionCodeletId) {
		this.selectionCodeletId = selectionCodeletId;
	}
    
        
}