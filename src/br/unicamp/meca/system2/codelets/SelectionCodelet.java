/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.meca.util.AbstractObjectPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *
 * @author wander
 */
public abstract class SelectionCodelet extends Codelet{
    
    protected String id;
    
    protected Memory predictedMemory;
    protected Memory predictedSituationMemory;
    protected Memory nextActionMemory;
    
    protected AbstractObject predictedSituation;
    protected String nextAction;
    
    /** We need a reference to the SelectionCodelet which will read the outputs of this ExpectationCodelet*/
	protected String expectationCodeletId;
    
    /** We need a reference to the SelectionCodelet which will read the outputs of this ExpectationCodelet*/
	protected String nextActionMemoryId;

    //private Map<String, AbstractObjectPair> predictedActionEffect = new HashMap<>();
    //private Map<String, double[]> encodedPredictedActionEffect = new HashMap<>();
    

    
    public SelectionCodelet(String id, String expectationCodeletId){
        super();
	this.id = id;
        this.expectationCodeletId = expectationCodeletId;
        setName(id);
    }
    

    @Override
    public void proc() {
        HashMap<String, AbstractObject> selection = policy();
        
        nextAction = selection.keySet().iterator().next();
        
        predictedSituation = selection.get(nextAction);
        

        predictedSituationMemory.setI(predictedSituation);
        predictedSituationMemory.setEvaluation(predictedMemory.getEvaluation());
            
        nextActionMemory.setI(nextAction);
        nextActionMemory.setEvaluation(predictedMemory.getEvaluation());

    }
    
    
    public abstract HashMap<String, AbstractObject> policy();
    
    public String getHighestAppraisalAction(Map<String, AbstractObjectPair> list){
        AbstractObjectPair better = new AbstractObjectPair();
        List<String> actions = new ArrayList<>();
        String action = "";
        for(AbstractObjectPair pair : list.values()){
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
    
    public AbstractObject getHighestAppraisalConfiguration(Map<String, AbstractObjectPair> list, String choosen){
        AbstractObjectPair better = new AbstractObjectPair();
        better = list.get(choosen);
        /*for(AbstractObjectPair pair : list.values()){
            if(better.getAppraisal() == null){
                better = pair;
            }
            else if(pair.getAppraisal().getEvaluation() > better.getAppraisal().getEvaluation()){
                better = pair;
                //action = list.get(pair);
            }
        }*/
        
        return better.getAfter();
        //return action;
    }
    
    public void getRandomAction(Map<String, AbstractObjectPair> list, Random rand){ 
        nextAction = (String)list.keySet().toArray()[rand.nextInt(list.keySet().size())];
        AbstractObjectPair temp = (AbstractObjectPair)list.values().toArray()[rand.nextInt(list.values().size())]; 
        predictedSituation = temp.getAfter();
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
