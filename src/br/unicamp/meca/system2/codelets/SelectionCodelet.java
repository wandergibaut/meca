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
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.meca.util.Episode;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *
 * @author W. Gibaut
 */
public abstract class SelectionCodelet extends Codelet{
    
    protected String id;
    
    protected Memory predictedMemory;
    protected Memory predictedSituationMemory;
    protected Memory nextActionMemory;
    
    protected INDArray predictedSituation;
    protected String nextAction;
    
    /** We need a reference to the SelectionCodelet which will read the outputs of this ExpectationCodelet*/
	protected String expectationCodeletId;
    
    /** We need a reference to the SelectionCodelet which will read the outputs of this ExpectationCodelet*/
	protected String nextActionMemoryId;

    //private Map<String, Episode> predictedActionEffect = new HashMap<>();
    //private Map<String, double[]> encodedPredictedActionEffect = new HashMap<>();
    

    
    public SelectionCodelet(String id, String expectationCodeletId){
        super();
	this.id = id;
        this.expectationCodeletId = expectationCodeletId;
        setName(id);
    }
    

    @Override
    public void proc() {
        HashMap<String, INDArray> selection = policy();
        
        nextAction = selection.keySet().iterator().next();
        
        predictedSituation = selection.get(nextAction);
        

        predictedSituationMemory.setI(predictedSituation);
        predictedSituationMemory.setEvaluation(predictedMemory.getEvaluation());
            
        nextActionMemory.setI(nextAction);
        nextActionMemory.setEvaluation(predictedMemory.getEvaluation());

    }
    
    
    public abstract HashMap<String, INDArray> policy();
    
    public String getHighestAppraisalAction(Map<String, Episode> list){
        Episode better = new Episode();
        List<String> actions = new ArrayList<>();
        String action = "";
        for(Episode pair : list.values()){
            if(better.getAppraisal() == null){
                better = pair;
                for(String key : list.keySet()){
                    if(list.get(key).equals(pair)){
                        action = key;
                    }
                }
            }
            else if(pair.getAppraisal().getEvaluation() > better.getAppraisal().getEvaluation()){
                better = pair;
                for(String key : list.keySet()){
                    if(list.get(key).equals(pair)){
                        action = key;
                        actions.clear();
                    }
                }
            }
            else if(pair.getAppraisal().getEvaluation() == better.getAppraisal().getEvaluation()){
                for(String key : list.keySet()){
                    if(list.get(key).equals(pair)){
                        actions.add(action);
                        actions.add(key);
                    }
                }
            }
        }
        //return better;
        
        if(!actions.isEmpty()){
            Random rand = new Random();
            return actions.get(rand.nextInt(actions.size()));
        }
        else{
            return action;
        }
        
        //return action;
    }
    
    public INDArray getHighestAppraisalConfiguration(Map<String, Episode> list, String choosen){
        Episode better = new Episode();
        better = list.get(choosen);
        /*for(Episode pair : list.values()){
            if(better.getAppraisal() == null){
                better = pair;
            }
            else if(pair.getAppraisal().getEvaluation() > better.getAppraisal().getEvaluation()){
                better = pair;
                //action = list.get(pair);
            }
        }*/
        
        return better.getTerminalState();
        //return action;
    }
    
    public void getRandomAction(Map<String, Episode> list, Random rand){
        nextAction = (String)list.keySet().toArray()[rand.nextInt(list.keySet().size())];
        Episode temp = (Episode)list.values().toArray()[rand.nextInt(list.values().size())];
        predictedSituation = temp.getTerminalState();
    }
    
    public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Selection Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
        
        /**
	 * @return the expectationCodeletId
	 */
	public String getExpectationCodeletId() {
		return expectationCodeletId;
	}


	/**
	 * @param expectationCodeletId the expectationCodeletId to set
	 */
	public void setExpectationCodeletId(String expectationCodeletId) {
		this.expectationCodeletId = expectationCodeletId;
	}
        

}
