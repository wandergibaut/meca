/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP and Ericsson Research                  *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     R. R. Gudwin, A. L. O. Paraense, E. Froes, W. Gibaut, S. de Paula,      * 
 *     E. Castro, V. Figueredo and K. Raizer                                   *
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.mind;

import br.unicamp.cst.core.entities.Codelet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;

import br.unicamp.cst.core.entities.Mind;
import br.unicamp.meca.memory.WorkingMemory;
import br.unicamp.meca.system1.codelets.*;
import br.unicamp.meca.system2.codelets.AttentionCodelet;
import br.unicamp.meca.system2.codelets.AppraisalCodelet;
import br.unicamp.meca.system2.codelets.ConsciousnessCodelet;
import br.unicamp.meca.system2.codelets.EpisodicAttentionCodelet;
import br.unicamp.meca.system2.codelets.EpisodicLearningCodelet;
import br.unicamp.meca.system2.codelets.EpisodicRetrievalCodelet;
import br.unicamp.meca.system2.codelets.ExpectationCodelet;
import br.unicamp.meca.system2.codelets.GoalCodelet;
import br.unicamp.meca.system2.codelets.LearningCodelet;
import br.unicamp.meca.system2.codelets.PerceptualBufferAttentionCodelet;
import br.unicamp.meca.system2.codelets.PlannerCodelet;
import br.unicamp.meca.system2.codelets.SelectionCodelet;
import java.util.Iterator;


/**
 * This class represents the MECA's agent mind.This is the main class to be used
 * by any MECA user.
 * 
 * @author A. L. O. Paraense
 * @author E. Froes
 * @author R. R. Gudwin
 * @author W. Gibaut
 * @see Mind
 * 
 */
public class MecaMind extends Mind {

	public static final String ACTION_SEQUENCE_PLAN_ID = "ExecutivePlan";
	public static final String ACTION_SEQUENCE_PLAN_REQUEST_ID = "PlanRequest";

	/*
	 * System 1
	 */

	private List<ISensoryCodelet> sensoryCodelets;
	private List<PerceptualCodelet> perceptualCodelets;

	private List<MotivationalCodelet> motivationalCodelets;
	private S1To2AttentionCodelet attentionCodeletSystem1;
	private List<ActivityCodelet> activityCodelets;
	private List<BehaviorCodelet> behaviorCodelets;
	private List<MoodCodelet> moodCodelets;
	private List<EmotionalCodelet> emotionalCodelets;
    private AdaptationCodelet adaptationCodelet;
	private List<IMotorCodelet> motorCodelets;


	private Memory actionSequencePlanMemoryContainer;
	private Memory actionSequencePlanRequestMemoryContainer;
	private ActivityTrackingCodelet activityTrackingCodelet;
        private static HashMap<String,String> memoryGroups = new HashMap();

	/*
	 * System 2
	 */

	private List<br.unicamp.meca.system2.codelets.AttentionCodelet> attentionCodeletsSystem2;
	private EpisodicLearningCodelet episodicLearningCodelet;
	private EpisodicRetrievalCodelet episodicRetrievalCodelet;
	private PerceptualBufferAttentionCodelet perceptualBufferAttentionCodelet;
	private EpisodicAttentionCodelet episodicAttentionCodelet;
	private ExpectationCodelet expectationCodelet;
	private SelectionCodelet selectionCodelet;
	private LearningCodelet learningCodelet;
	private ConsciousnessCodelet consciousnessCodelet;
	private PlannerCodelet plannerCodelet;
	private GoalCodelet goalCodelet;
	private AppraisalCodelet appraisalCodelet;
	private WorkingMemory workingMemory;

	private String id;

	/**
	 * Creates the MECA Mind.
	 */
	public MecaMind() {
		setId(UUID.randomUUID().toString());
		setWorkingMemory(new WorkingMemory(getId()));
                createCodeletGroup("Sensory");
                createCodeletGroup("Motor");
                createCodeletGroup("Perception");
                createCodeletGroup("Activity");
                createCodeletGroup("Motivational");
                createCodeletGroup("Behavioral");
                createCodeletGroup("ActivityTracking");
                createMemoryGroup("Sensors");
                createMemoryGroup("Actuators");
                createMemoryGroup("Percepts");
                createMemoryGroup("Drives");
                createMemoryGroup("Plans");
	}

	/**
	 * Creates the MECA Mind.
	 * 
	 * @param id
	 *            the id of the MECA mind. Must be unique per MECA mind.
	 */
	public MecaMind(String id) {
		setId(id);
		setWorkingMemory(new WorkingMemory(getId()/*, attentionCodeletSystem1.getId(), episodicRetrievalCodelet.getId()*/));

		setWorkingMemory(new WorkingMemory(getId()));
                createCodeletGroup("Sensory");
                createCodeletGroup("Motor");
                createCodeletGroup("Perception");
                createCodeletGroup("Activity");
                createCodeletGroup("Motivational");
                createCodeletGroup("Behavioral");
                createCodeletGroup("ActivityTracking");
                createMemoryGroup("Sensors");
                createMemoryGroup("Actuators");
                createMemoryGroup("Percepts");
                createMemoryGroup("Drives");
                createMemoryGroup("Plans");
	}

	/**
	 * Mounts the MECA Mind. After creating the MECA Mind's instance and setting
	 * all the codelets inside it, this method is responsible for binding
	 * together all codelets inside the mind, creating memories (objects and
	 * containers) and setting them either as inputs or outputs of each codelet,
	 * according to MECA's reference architecture.
	 * <p>
	 * This method must be called before running the MECA Agent.
	 */
	public void mountMecaMind() {
		mountSensoryCodelets();
		mountPerceptualCodelets();
		mountMotorCodelets();
		mountAttentionCodelets();
		mountWorkingMemory();
		mountPlannerCodelet();

		mountMotivationalCodelets();
		mountActionSequencePlanMemory();
		mountBehaviorCodelets();

		mountActivityTrackingCodelet();
		mountActivityCodelets();
		mountEpisodicCodelets();
		mountExpectationCodelet();
        mountSelectionCodelet();
        mountLearningCodelet();
        mountModules();
        mountAdaptationCodelet();
		mountConsciousnessCodelet();
		mountMemoryGroups();
	}

	private void mountActionSequencePlanMemory() {
		actionSequencePlanMemoryContainer = createMemoryContainer(ACTION_SEQUENCE_PLAN_ID);
		actionSequencePlanRequestMemoryContainer = createMemoryContainer(ACTION_SEQUENCE_PLAN_REQUEST_ID);
	}
        
        
        private void mountMemoryGroups() {
            Iterator it = memoryGroups.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    registerMemory((String)pair.getKey(),(String)pair.getValue() );
                    it.remove(); // avoids a ConcurrentModificationException
                }
        }

	private void mountModules() {
		if (getPlannerCodelet() != null) {
			//getPlansSubsystemModule().setjSoarCodelet(getSoarCodelet());
		}
	}

	private void mountPerceptualCodelets() {
		if (perceptualCodelets != null) {
			for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {
				if (perceptualCodelet != null && perceptualCodelet.getId() != null) {
					insertCodelet(perceptualCodelet);
					/*
					 * Inputs
					 */
					if (sensoryCodelets != null) {
						for (ISensoryCodelet sensoryCodelet : sensoryCodelets) {
							if (sensoryCodelet != null && sensoryCodelet.getId() != null) {
								ArrayList<String> sensoryCodeletsIds = perceptualCodelet.getSensoryCodeletsIds();
								if (sensoryCodeletsIds != null) {
									for (String sensoryCodeletId : sensoryCodeletsIds) {
										if (sensoryCodeletId != null && sensoryCodeletId.equalsIgnoreCase(sensoryCodelet.getId())) {
											perceptualCodelet.addInputs(sensoryCodelet.getOutputs());
										}
									}
								}
							}
						}
					}
					/*
					 * Output
					 */
					MemoryObject perceptualMemory = createMemoryObject(perceptualCodelet.getId());
					perceptualCodelet.addOutput(perceptualMemory);
				}
			}
		}
	}

	private void mountSensoryCodelets() {
		if (sensoryCodelets != null) {
			for (ISensoryCodelet sensoryCodelet : sensoryCodelets) {
				if (sensoryCodelet != null && sensoryCodelet.getId() != null) {
					insertCodelet((Codelet) sensoryCodelet);
					/*
					 * Output
					 */
					MemoryObject sensoryMemory = createMemoryObject(sensoryCodelet.getId());
					sensoryCodelet.addOutput(sensoryMemory);
				}
			}
		}
	}

	private void mountMotivationalCodelets() {
		if (getMotivationalCodelets() != null) {
			for (MotivationalCodelet motivationalCodelet : getMotivationalCodelets()) {
				/*
				 * Input Sensors
				 */
				if (motivationalCodelet.getSensoryCodeletsIds() != null) {
					List<String> sensoryIds = motivationalCodelet.getSensoryCodeletsIds();
					for (String sensoryId : sensoryIds) {
						if (sensoryCodelets != null) {
							for (ISensoryCodelet sensoryCodelet : sensoryCodelets) {
								if (sensoryCodelet.getId().equals(sensoryId)) {
									motivationalCodelet.addInputs(sensoryCodelet.getOutputs());
								}
							}
						}
					}
				}
				/*
				 * Input Drives
				 */
				if (motivationalCodelet.getMotivationalCodeletsIds() != null) {
					HashMap<String, Double> motivationalCodeletsIds = motivationalCodelet.getMotivationalCodeletsIds();
					for (Map.Entry<String, Double> motivationalCodeletId : motivationalCodeletsIds.entrySet()) {
						for (MotivationalCodelet motivationalCodeletInput : getMotivationalCodelets()) {
							if (motivationalCodeletInput.getId().equals(motivationalCodeletId.getKey())) {
								HashMap<Memory, Double> driveRelevance = new HashMap<>();
								driveRelevance.put(motivationalCodeletInput.getOutputDriveMO(),
										motivationalCodeletId.getValue());
								motivationalCodelet.addInput(this.createMemoryObject(
										motivationalCodeletInput.getOutputDriveMO().getName(), driveRelevance));
							}
						}
					}
				}
				/*
				 * Output Drives
				 */
				MemoryObject outputDrive = this.createMemoryObject(motivationalCodelet.getId() + "Drive");
				motivationalCodelet.addOutput(outputDrive);
				insertCodelet(motivationalCodelet);
			}
		}
	}
	
	private void mountActivityTrackingCodelet() {
            
            ArrayList<String> perceptualIds = new ArrayList<>();
            for (PerceptualCodelet perception : perceptualCodelets)
                perceptualIds.add(perception.getId());
            activityTrackingCodelet = new ActivityTrackingCodelet("ActivityTracking", perceptualIds);
            registerCodelet(activityTrackingCodelet, "ActivityTracking");
            registerMemory(ACTION_SEQUENCE_PLAN_ID,"Plans");
            registerMemory(ACTION_SEQUENCE_PLAN_REQUEST_ID,"Plans");

            if (activityTrackingCodelet != null && activityTrackingCodelet.getId() != null
				&& activityTrackingCodelet.getPerceptualCodeletsIds() != null) {	
		/*
		 * Inputs
		 */
		activityTrackingCodelet.addInput(actionSequencePlanMemoryContainer);

		if(perceptualCodelets != null) {
				for(PerceptualCodelet perceptualCodelet : perceptualCodelets) {
					if(perceptualCodelet != null && perceptualCodelet.getId() != null) {
						ArrayList<String> perceptualCodeletsIds = activityTrackingCodelet.getPerceptualCodeletsIds();
						if(perceptualCodeletsIds != null) {
							for(String perceptualCodeletId : perceptualCodeletsIds) {
								if(perceptualCodeletId != null && perceptualCodelet.getId().equalsIgnoreCase(perceptualCodeletId)) {
									activityTrackingCodelet.addInputs(perceptualCodelet.getOutputs());
								}
							}
						}
					}
				}
			}
			insertCodelet(activityTrackingCodelet);
		}

	}


	private void mountBehaviorCodelets() {
		if (behaviorCodelets != null) {
			for (BehaviorCodelet behaviorCodelet : behaviorCodelets) {
				if (behaviorCodelet != null && behaviorCodelet.getId() != null
						&& behaviorCodelet.getMotivationalCodeletsIds() != null
						&& behaviorCodelet.getPerceptualCodeletsIds() != null) {
					/*
					 * Outputs
					 */										
					behaviorCodelet.addOutput(actionSequencePlanMemoryContainer);
					behaviorCodelet.addOutput(actionSequencePlanRequestMemoryContainer);
					/*
					 * Inputs
					 */
					if (motivationalCodelets != null) {
						for (MotivationalCodelet motivationalCodelet : motivationalCodelets) {
							if (motivationalCodelet != null && motivationalCodelet.getId() != null) {
								ArrayList<String> motivationalCodeletsIds = behaviorCodelet.getMotivationalCodeletsIds();
								if (motivationalCodeletsIds != null) {
									for (String motivationalCodeletId : motivationalCodeletsIds) {
										if (motivationalCodeletId != null && motivationalCodelet.getId().equalsIgnoreCase(motivationalCodeletId)) {
											behaviorCodelet.addInputs(motivationalCodelet.getOutputs());
										}
									}
								}
							}
						}
					}
					if(perceptualCodelets != null) {
						for(PerceptualCodelet perceptualCodelet : perceptualCodelets) {
							if(perceptualCodelet != null && perceptualCodelet.getId() != null) {
								ArrayList<String> perceptualCodeletsIds = behaviorCodelet.getPerceptualCodeletsIds();
								if(perceptualCodeletsIds != null) {
									for(String perceptualCodeletId : perceptualCodeletsIds) {
										if(perceptualCodeletId != null && perceptualCodelet.getId().equalsIgnoreCase(perceptualCodeletId)) {
											behaviorCodelet.addInputs(perceptualCodelet.getOutputs());
										}
									}
								}
							}
						}
					}

					if (plannerCodelet != null && plannerCodelet.getId() != null && behaviorCodelet.getPlannerCodeletId() != null) {
						if (plannerCodelet.getId().equalsIgnoreCase(behaviorCodelet.getPlannerCodeletId())) {
							behaviorCodelet.addBroadcasts(plannerCodelet.getOutputs());
						}
					}
					insertCodelet(behaviorCodelet);
				}
			}
		}
	}

	private void mountActivityCodelets() {
		if (activityCodelets != null) {
			for (ActivityCodelet activityCodelet : activityCodelets) {
				if (activityCodelet != null && activityCodelet.getId() != null
						&& activityCodelet.getPerceptualCodeletsIds() != null
						&& activityCodelet.getMotivationalCodeletsIds() != null
						&& activityCodelet.getMotorCodeletId() != null) {
					insertCodelet(activityCodelet);
					/*
					 * Outputs
					 */
					if (motorCodelets != null) {
						for (IMotorCodelet motorCodelet : motorCodelets) {
							if (motorCodelet != null && motorCodelet.getId() != null) {
								if (motorCodelet.getId().equalsIgnoreCase(activityCodelet.getMotorCodeletId())) {
									activityCodelet.addOutputs(motorCodelet.getInputs());
								}
                                                        }
							}
						}
					}
					/*
					 * Inputs
					 */
					if (motivationalCodelets != null) {
						for (MotivationalCodelet motivationalCodelet : motivationalCodelets) {
							if (motivationalCodelet != null && motivationalCodelet.getId() != null) {
								ArrayList<String> motivationalCodeletsIds = activityCodelet.getMotivationalCodeletsIds();
								if (motivationalCodeletsIds != null) {
									for (String motivationalCodeletId : motivationalCodeletsIds) {
										if (motivationalCodeletId != null && motivationalCodelet.getId().equalsIgnoreCase(motivationalCodeletId)) {
											activityCodelet.addInputs(motivationalCodelet.getOutputs());
										}
									}
								}
							}
						}
					}

					if (perceptualCodelets != null) {
						for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {
							if (perceptualCodelet != null && perceptualCodelet.getId() != null) {
								ArrayList<String> perceptualCodeletsIds = activityCodelet.getPerceptualCodeletsIds();
								if (perceptualCodeletsIds != null) {
									for (String perceptualCodeletId : perceptualCodeletsIds) {
										if (perceptualCodeletId != null && perceptualCodelet.getId().equalsIgnoreCase(perceptualCodeletId)) {
											activityCodelet.addInputs(perceptualCodelet.getOutputs());
										}
									}
								}
							}
						}
					}


					if (plannerCodelet != null && plannerCodelet.getId() != null && activityCodelet.getPlannerCodeletId() != null) {
						if (plannerCodelet.getId().equalsIgnoreCase(activityCodelet.getPlannerCodeletId())) {
							activityCodelet.addBroadcasts(plannerCodelet.getOutputs());

						}
					}
					activityCodelet.addInput(actionSequencePlanMemoryContainer);
				}
			}
		}


	private void mountMotorCodelets() {
		if (motorCodelets != null) {
			for (IMotorCodelet motorCodelet : motorCodelets) {
				if (motorCodelet != null && motorCodelet.getId() != null) {
					insertCodelet((Codelet) motorCodelet);
					/*
					 * Input
					 */
					Memory motorMemoryContainer = createMemoryContainer(motorCodelet.getId());
					motorCodelet.addInput(motorMemoryContainer);
				}
			}
		}
	}

	private void mountAttentionCodelets() {
		if (attentionCodeletSystem1 != null) {
			/*
			 * Inputs
			 */
			if (perceptualCodelets != null) {
				for (String inputPerceptualId : attentionCodeletSystem1.getPerceptualCodeletsIds()) {
					for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {
						if (inputPerceptualId.equals(perceptualCodelet.getId())) {
							attentionCodeletSystem1.addInputs(perceptualCodelet.getOutputs());
						}
					}
				}
			}
			/*
			 * Outputs
			 */
			Memory attentionMemoryOutput = createMemoryObject(attentionCodeletSystem1.getId());
			attentionCodeletSystem1.addOutput(attentionMemoryOutput);
			attentionCodeletSystem1.setOutputFilteredPerceptsMO(attentionMemoryOutput);
			insertCodelet(attentionCodeletSystem1);
		}

		if(attentionCodeletsSystem2 != null){
			for(AttentionCodelet attention : attentionCodeletsSystem2){
				//String[] className = attention.getClass().getName().split("\\.");

				//olhar isso aqui
				if(attention instanceof  PerceptualBufferAttentionCodelet){
					//if(className[4].equals("PerceptualBufferAttentionCodelet")){
					attention.addOutput(createMemoryObject(attention.getId())); //cria um output com o nome do perceptual
					attention.addInput(attentionCodeletSystem1.getOutputFilteredPerceptsMO());
					setPerceptualBufferAttentionCodelet((PerceptualBufferAttentionCodelet)attention);
					insertCodelet(attention);
				}
				else if (attention instanceof EpisodicAttentionCodelet){
					attention.addOutput(createMemoryObject(attention.getId())); //cria um output com o nome do perceptual
					attention.addInputs(getPerceptualBufferAttentionCodelet().getOutputs());
					setEpisodicAttentionCodelet((EpisodicAttentionCodelet)attention);
					insertCodelet(attention);
				}
			}
		}
	}
        
	private void mountEpisodicCodelets(){
		if(episodicLearningCodelet != null){
			episodicLearningCodelet.addInputs(episodicAttentionCodelet.getOutputs());
			episodicLearningCodelet.addOutput(createMemoryObject(episodicLearningCodelet.getId()));
			insertCodelet(episodicLearningCodelet);
		}

		if(episodicRetrievalCodelet != null){
			episodicRetrievalCodelet.addInputs(episodicLearningCodelet.getOutputs());
			//episodicRetrievalCodelet.addInput(getWorkingMemory().getCueMemory());
			episodicRetrievalCodelet.addInputs(attentionCodeletSystem1.getOutputs());
			episodicRetrievalCodelet.addOutput(createMemoryObject(episodicRetrievalCodelet.getId()));
			insertCodelet(episodicRetrievalCodelet);
		}
	}

	private void mountPlannerCodelet() {
		if (plannerCodelet != null) {
			plannerCodelet.addInput(createMemoryObject(WorkingMemory.WORKING_MEMORY_INPUT, getWorkingMemory()));
			plannerCodelet.addOutput(createMemoryObject(plannerCodelet.getId()));
			insertCodelet(plannerCodelet);
		}
	}

	private void mountExpectationCodelet(){
		if(expectationCodelet != null){
			expectationCodelet.addInput(getWorkingMemory().getCurrentPerceptionMemory());
			expectationCodelet.addInput(createMemoryObject(learningCodelet.getId()));
			expectationCodelet.addInputs(episodicRetrievalCodelet.getOutputs());
			expectationCodelet.addOutput(createMemoryObject(selectionCodelet.getId()));
			expectationCodelet.addOutput(createMemoryObject(expectationCodelet.getId()));
			insertCodelet(expectationCodelet);
		}
	}
        
        //private void mountExpectationCodeletWithPlanner(){}
        
	private void mountSelectionCodelet(){
		if(selectionCodelet != null){
			selectionCodelet.addInputs(expectationCodelet.getOutputs());
			selectionCodelet.addOutput(getWorkingMemory().getNextActionMemory());
			selectionCodelet.addOutput(getWorkingMemory().getPredictedSituationMemory());
			insertCodelet(selectionCodelet);
		}
	}
        
	private void mountLearningCodelet(){
		if(learningCodelet != null){
			//weights output
			learningCodelet.addInput(getWorkingMemory().getCurrentPerceptionMemory());
			learningCodelet.addInput(createMemoryObject(expectationCodelet.getId()));
			learningCodelet.addOutput(createMemoryObject(learningCodelet.getId()));
			insertCodelet(learningCodelet);
		}
	}
        
    //TODO: nextMemory and predictedSituation
	private void mountWorkingMemory() {
		if (getWorkingMemory() != null) {
			if (attentionCodeletSystem1 != null) {
				getWorkingMemory().setCurrentPerceptionMemory(attentionCodeletSystem1.getOutputFilteredPerceptsMO());
                                getWorkingMemory().getCueMemory().setType(attentionCodeletSystem1.getId());
			}
			if(episodicRetrievalCodelet != null){
			    getWorkingMemory().getEpisodicRecallMemory().setType(episodicRetrievalCodelet.getId());
			}
			getWorkingMemory().getPredictedSituationMemory().setType("predictedSituation");

		}
	}
        
	private void mountAdaptationCodelet(){
		if(adaptationCodelet != null && adaptationCodelet.getPerceptualCodeletsIds() != null
				&& adaptationCodelet.getMotorCodeletIds() != null){

			if (motorCodelets != null) {
				for (IMotorCodelet motorCodelet : motorCodelets) {
					if (motorCodelet != null && motorCodelet.getId() != null) {
						for(int i=0; i < adaptationCodelet.getMotorCodeletIds().size(); i++){
							if (motorCodelet.getId()
									.equalsIgnoreCase(adaptationCodelet.getMotorCodeletIds().get(i))) {
								adaptationCodelet.addOutputs(motorCodelet.getInputs());
							}
						}
					}
				}
			}
			/*
			 * Inputs
			 */
			if (perceptualCodelets != null) {
				for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {
					if (perceptualCodelet != null && perceptualCodelet.getId() != null) {
						ArrayList<String> perceptualCodeletsIds = adaptationCodelet.getPerceptualCodeletsIds();
						if (perceptualCodeletsIds != null) {
							for (String perceptualCodeletId : perceptualCodeletsIds) {
								if (perceptualCodeletId != null
										&& perceptualCodelet.getId().equalsIgnoreCase(perceptualCodeletId)) {
									adaptationCodelet.addInputs(perceptualCodelet.getOutputs());
								}
							}
						}
					}
				}
			}
			if (plannerCodelet != null && plannerCodelet.getId() != null) {
				if (plannerCodelet.getId().equalsIgnoreCase(adaptationCodelet.getPlannerCodeletId())) {
					adaptationCodelet.addBroadcasts(plannerCodelet.getOutputs());
				}
			}
			insertCodelet(adaptationCodelet);
		}
	}


	private void mountConsciousnessCodelet(){
		if(consciousnessCodelet != null){
			insertCodelet(consciousnessCodelet);
		}
	}

	/**
	 * Sets the Sensory Codelets.
	 * 
	 * @deprecated instead, add Sensory Codelets using the interface ISensoryCodelet
	 * 
	 * @param sensoryCodelets
	 *            the sensoryCodelets to set
	 */
	@Deprecated
	public void setSensoryCodelets(List<SensoryCodelet> sensoryCodelets) {
		this.sensoryCodelets = new ArrayList<ISensoryCodelet>();
		this.sensoryCodelets.addAll(sensoryCodelets);
	}

	/**
	 * Sets the Perceptual Codelets.
	 * 
	 * @param perceptualCodelets
	 *            the perceptualCodelets to set
	 */
	public void setPerceptualCodelets(List<PerceptualCodelet> perceptualCodelets) {
		this.perceptualCodelets = perceptualCodelets;
	}

	/**
	 * Sets the Motivational Codelets.
	 * 
	 * @param motivationalCodelets
	 *            the motivationalCodelets to set
	 */
	public void setMotivationalCodelets(List<MotivationalCodelet> motivationalCodelets) {
		this.motivationalCodelets = motivationalCodelets;
	}

	/**
	 * Sets the System 1 Attention Codelet.
	 * 
	 * @param attentionCodeletSystem1
	 *            the attentionCodeletSystem1 to set
	 */
	public void setAttentionCodeletSystem1(S1To2AttentionCodelet attentionCodeletSystem1) {
		this.attentionCodeletSystem1 = attentionCodeletSystem1;
	}

	/**


	/**
	 * Sets the Motor Codelets.
	 * 
	 * @deprecated instead, add Motor Codelets using the interface IMotorCodelet
	 * @param motorCodelets
	 *            the motorCodelets to set
	 */
	@Deprecated
	public void setMotorCodelets(List<MotorCodelet> motorCodelets) {
		this.motorCodelets = new ArrayList<IMotorCodelet>();
		this.motorCodelets.addAll(motorCodelets);
	}

	/**
	 * Sets the System 2 Attention Codelets
	 * 
	 * @param attentionCodeletsSystem2
	 *            the attentionCodeletsSystem2 to set
	 */
	public void setAttentionCodeletsSystem2(
			List<br.unicamp.meca.system2.codelets.AttentionCodelet> attentionCodeletsSystem2) {
		this.attentionCodeletsSystem2 = attentionCodeletsSystem2;
	}

	/**
	 * Sets the Episodic Learning Codelet.
	 * 
	 * @param episodicLearningCodelet
	 *            the episodicLearningCodelet to set
	 */
	public void setEpisodicLearningCodelet(EpisodicLearningCodelet episodicLearningCodelet) {
		this.episodicLearningCodelet = episodicLearningCodelet;
	}

	/**
	 * Sets the Episodic Retrieval Codelet.
	 * 
	 * @param episodicRetrievalCodelet
	 *            the episodicRetrievalCodelet to set
	 */
	public void setEpisodicRetrievalCodelet(EpisodicRetrievalCodelet episodicRetrievalCodelet) {
		this.episodicRetrievalCodelet = episodicRetrievalCodelet;
	}

	/**
	 * Sets the Expectation Codelet.
	 * 
	 * @param expectationCodelet
	 *            the expectationCodelet to set
	 */
	public void setExpectationCodelet(ExpectationCodelet expectationCodelet) {
		this.expectationCodelet = expectationCodelet;
	}
        
        /**
	 * Sets the Selection Codelet.
	 * 
	 * @param selectionCodelet
	 *            the selectionCodelet to set
	 */
        public void setSelectionCodelet(SelectionCodelet selectionCodelet){
            this.selectionCodelet = selectionCodelet;
        }
        
        /**
	 * Sets the Learning Codelet.
	 * 
	 * @param learningCodelet
	 *            the learningCodelet to set
	 */
        public void setLearningCodelet(LearningCodelet learningCodelet){
            this.learningCodelet = learningCodelet;
        }

	/**
	 * Sets the Consciousness Codelet.
	 * 
	 * @param consciousnessCodelet
	 *            the consciousnessCodelet to set
	 */
	public void setConsciousnessCodelet(ConsciousnessCodelet consciousnessCodelet) {
		this.consciousnessCodelet = consciousnessCodelet;
	}

	/**
	 * Sets the Planner Codelet.
	 * 
	 * @param plannerCodelet
	 *            the plannerCodelet to set
	 */
	public void setPlannerCodelet(PlannerCodelet plannerCodelet) {
		this.plannerCodelet = plannerCodelet;
	}

	/**
	 * Sets the Goal Codelet.
	 * 
	 * @param goalCodelet
	 *            the goalCodelet to set
	 */
	public void setGoalCodelet(GoalCodelet goalCodelet) {
		this.goalCodelet = goalCodelet;
	}

	/**
	 * Sets the Appraisal Codelet.
	 * 
	 * @param appraisalCodelet
	 *            the appraisalCodelet to set
	 */
	public void setAppraisalCodelet(AppraisalCodelet appraisalCodelet) {
		this.appraisalCodelet = appraisalCodelet;
	}
        
	/**
	 * @param behaviorCodelets the behaviorCodelets to set
	 */
	public void setBehaviorCodelets(List<BehaviorCodelet> behaviorCodelets) {
		this.behaviorCodelets = behaviorCodelets;
	}

	/**
	 * @param sensoryCodelets the sensoryCodelets to set
	 */
	public void setISensoryCodelets(List<ISensoryCodelet> sensoryCodelets) {
		this.sensoryCodelets = sensoryCodelets;
	}

	/**
	 * @param motorCodelets the motorCodelets to set
	 */
	public void setIMotorCodelets(List<IMotorCodelet> motorCodelets) {
		this.motorCodelets = motorCodelets;
	}

	/**
	 * Gets the MECA Mind id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the MECA Mind id
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the Motivational Codelets.
	 * 
	 * @return the Motivational Codelets.
	 */
	public List<MotivationalCodelet> getMotivationalCodelets() {
		return motivationalCodelets;
	}

	/**
	 * Gets the Goal Codelets.
	 * 
	 * @return the Goal Codelet.
	 */
	public GoalCodelet getGoalCodelet() {
		return goalCodelet;
	}

	/**
	 * Gets the Appraisal Codelet.
	 * 
	 * @return the Appraisal Codelet.
	 */
	public AppraisalCodelet getAppraisalCodelet() {
		return appraisalCodelet;
	}

	/**
	 * Gets the Working Memory.
	 * 
	 * @return the Working Memory.
	 */
	public WorkingMemory getWorkingMemory() {
		return workingMemory;
	}

	/**
	 * Sets the Working Memory.
	 * 
	 * @param workingMemory
	 *            the working memory to set.
	 */
	public void setWorkingMemory(WorkingMemory workingMemory) {
		this.workingMemory = workingMemory;
	}

	/**
	 * Gets the Sensory Codelets.
	 * 
	 * @return the sensoryCodelets.
	 */
	public List<ISensoryCodelet> getSensoryCodelets() {
		return sensoryCodelets;
	}

	/**
	 * Gets the Perceptual Codelets.
	 * 
	 * @return the perceptualCodelets.
	 */
	public List<PerceptualCodelet> getPerceptualCodelets() {
		return perceptualCodelets;
	}

	/**
	 * Gets the Attention Codelet from System 1.
	 * 
	 * @return the attentionCodeletSystem1.
	 */
	public S1To2AttentionCodelet getAttentionCodeletSystem1() {
		return attentionCodeletSystem1;
	}

	/**
	 * Gets the Motor Codelets.
	 * 
	 * @return the motorCodelets.
	 */
	public List<IMotorCodelet> getMotorCodelets() {
		return motorCodelets;
	}

	/**
	 * Gets the Attention Codelets from System 2.
	 * 
	 * @return the attentionCodeletsSystem2.
	 */
	public List<br.unicamp.meca.system2.codelets.AttentionCodelet> getAttentionCodeletsSystem2() {
		return attentionCodeletsSystem2;
	}

	/**
	 * Gets the Episodic Learning Codelet.
	 * 
	 * @return the episodicLearningCodelet.
	 */
	public EpisodicLearningCodelet getEpisodicLearningCodelet() {
		return episodicLearningCodelet;
	}

	/**
	 * Gets the Episodic Retrieval Codelet.
	 * 
	 * @return the episodicRetrievalCodelet.
	 */
	public EpisodicRetrievalCodelet getEpisodicRetrievalCodelet() {
		return episodicRetrievalCodelet;
	}

	/**
	 * Gets the Expectation Codelet.
	 *
	 * @return the expectationCodelet.
	 */
	public ExpectationCodelet getExpectationCodelet() {
		return expectationCodelet;
	}

	/**
	 * Gets the Selection Codelet.
	 *
	 * @return the selectionCodelet.
	 */
	public SelectionCodelet getSelectionCodelet() {
		return selectionCodelet;
	}

	/**
	 * Gets the Consciousness Codelet.
	 * 
	 * @return the consciousnessCodelet.
	 */
	public ConsciousnessCodelet getConsciousnessCodelet() {
		return consciousnessCodelet;
	}

	/**
	 * Gets the Planner Codelet.
	 * 
	 * @return the plannerCodelet.
	 */
	public PlannerCodelet getPlannerCodelet() {
		return plannerCodelet;
	}

	public PerceptualBufferAttentionCodelet getPerceptualBufferAttentionCodelet(){
		return perceptualBufferAttentionCodelet;
	}
        
	public void setPerceptualBufferAttentionCodelet(PerceptualBufferAttentionCodelet perceptualBufferAttentionCodelet){
		this.perceptualBufferAttentionCodelet = perceptualBufferAttentionCodelet;
	}
        
	public EpisodicAttentionCodelet getEpisodicAttentionCodelet(){
		return this.episodicAttentionCodelet;
	}

	public void setEpisodicAttentionCodelet(EpisodicAttentionCodelet attention){
		this.episodicAttentionCodelet = attention;
	}
        
        
	public AdaptationCodelet getAdaptationCodelet(){
		return this.adaptationCodelet;
	}
        
	public void setAdaptationCodelet(AdaptationCodelet adaptation){
		this.adaptationCodelet = adaptation;
	}
        
	public void removeCodelet(Codelet codelet){
		this.codeRack.destroyCodelet(codelet);
	}
        
	public void removeBehavioralCodelet(BehaviorCodelet codelet){
		this.behaviorCodelets.remove(codelet);
		this.codeRack.destroyCodelet(codelet);
	}


	/**
	 * @return the behaviorCodelets
	 */
	public List<BehaviorCodelet> getBehaviorCodelets() {
		return behaviorCodelets;
	}

	/**
	 * @return the activityTrackingCodelet
	 */
	public ActivityTrackingCodelet getActivityTrackingCodelet() {
		return activityTrackingCodelet;
	}

	/**
	 * @param activityTrackingCodelet the activityTrackingCodelet to set
	 */
	public void setActivityTrackingCodelet(ActivityTrackingCodelet activityTrackingCodelet) {
		this.activityTrackingCodelet = activityTrackingCodelet;
	}

	/**
	 * @return the activityCodelets
	 */
	public List<ActivityCodelet> getActivityCodelets() {
		return activityCodelets;
	}
        
        public void pregisterMemory(String memoryName, String memoryGroup) {
            memoryGroups.put(memoryName, memoryGroup);
        }

	/**
	 * @param activityCodelets the activityCodelets to set
	 */
	public void setActivityCodelets(List<ActivityCodelet> activityCodelets) {
		this.activityCodelets = activityCodelets;
	}

}
