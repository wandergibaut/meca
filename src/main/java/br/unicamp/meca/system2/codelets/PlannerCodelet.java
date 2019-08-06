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
import br.unicamp.meca.memory.WorkingMemory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author W. Gibaut
 */
public abstract class PlannerCodelet extends Codelet{
        protected String id;

	protected WorkingMemory workingMemory;

	//private List<Object> rawPlan = null;

	protected Memory workingMemoryOutputMO;

	protected Memory workingMemoryInputMO;

    
    public PlannerCodelet(String id) {

		this.id = id;
		setName(id);

		//rawPlan = new ArrayList<>();
	}
    
    @Override
    public void accessMemoryObjects() {
        if (workingMemoryInputMO == null) {
			workingMemoryInputMO = this.getInput(WorkingMemory.WORKING_MEMORY_INPUT);
			workingMemory = (WorkingMemory) workingMemoryInputMO.getI();
		}

		if (workingMemoryOutputMO == null)
			workingMemoryOutputMO = this.getOutput(id);
    }

    @Override
    public void proc() {
        List<String> actionPlan =  processWorkingMemory();
        
        boolean newPlan = true;
        
        if (actionPlan != null && !actionPlan.isEmpty()) {
                        ArrayList<Memory> memories = workingMemory.getPlansMemory().getAllMemories();
                        
                        for(Memory memory : memories){
                            if(memory.getI().equals(actionPlan)){
                                newPlan = false;
                                // do nothing. There already a plan like that
                            }
                        }
                        
                        if(newPlan){
                            workingMemory.getPlansMemory().setI(actionPlan);

                            workingMemoryOutputMO.setI(workingMemory);
                        }
			//fromPlanToAction(); outro Codelet faz isso
	}
    }
    
    // onde a magica acontece. Deve utlizar as infos relevantes pra a aplicação e retornar uma lista de Strings
    //da pra usar uma dinâmica de transição pra prever o proximo estado e definir as coisas
    
    public abstract List<String> processWorkingMemory();
    
    //a dinamica de transição
    //public abstract double[] transition(double[] input2dim, String action);
    
    
    //public abstract String choosenAction(double[] outputs);
    
    

    
        /**
	 * Tells if the object is an abstract object.
	 * 
	 * @param obj
	 *            the object to be tested
	 * @return true if the obj is an abstract object.
	 */
	public boolean isAbstractObject(Object obj) {

		if (obj.getClass() == AbstractObject.class)
			return true;
		else
			return false;
	}

	/**
	 * Tells if the object is a Java String.
	 * 
	 * @param obj
	 *            the object to be tested
	 * @return true if the obj is a Java String.
	 */
	public boolean isString(Object obj) {

		if (obj.getClass() == String.class)
			return true;
		else
			return false;
	}

	/**
	 * Gets the Soar Codelet id.
	 * 
	 * @return the Soar Codelet id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the Soar Codelet id.
	 * 
	 * @param id
	 *            the Soar codelet id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the Working memory
	 * 
	 * @return the working memoryy.
	 */
	public WorkingMemory getWorkingMemory() {
		return this.workingMemory;
	}
}
