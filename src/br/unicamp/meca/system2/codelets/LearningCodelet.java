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
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;
//import com.google.common.primitives.Doubles;
import java.util.Arrays;
import java.util.List;
import br.unicamp.meca.util.Encoder;

/**
 *
 * @author W. Gibaut
 */
public abstract class LearningCodelet extends Codelet{
    
    protected String id;
    
    //private Memory predictedPoolMO;
    //private Memory actionPoolMemory;
    private Memory currentPerceptionMemory;
    
    
    protected String expectationCodeletId;
    
    
    /*private Memory appraisalMO;
    private Memory weightsMO;
    private Memory weightsInputMO;
    
    private Memory currentPerceptionMO;
    private Memory predictedSituationMO;
    private Memory nextAction;
    
    private Memory preditorWeightsMO;
    private Memory broadcastedResetMO;
    private Memory deathMemory;
    
    //private Memory selectionFlagMemory;
    //private Memory currentPerceptionMemoryCopy;
    
    //private List<float[][]> allDataTogether = new ArrayList<>();
    //private float[][] recordedInputsActionCondition;
    //private float[][] recordedActualOutputs;
    private List<List<double[]>> allDataPreditor = new ArrayList<>();
    
    private List<double[]> allDataTogether = new ArrayList<>();
    private List<double[]> recordedInputsActionCondition = new ArrayList<>();
    private List<double[]> recordedActualOutputs = new ArrayList<>();
    
    private List<double[]> recordedActionTakenEncoded = new ArrayList<>();
    private List<double[]> recordedNextOutputs = new ArrayList<>();
    private List<double[]> recordedRewards = new ArrayList<>();
    
    private List<double[]> tempEncoded = new ArrayList<>();
    private List<double[]> networkOutputRecord = new ArrayList<>();
    
    private int oldTempSize = 0;
    
    //adicionar os elementos da memoria episodica como entradas
    */
    private Encoder encoder;
    /*private AbstractObject oldPerception;// = new AbstractObject("old_Perception");
    private int executionCounter = 0;
    private int debbugerCounter = 0;
    private int oldSize = 0;
    
    
    private double lastPerceptionReceivedPackID = 0;
    private double lastCommandReceivedID = 0;
    private double deathEval = 0;
    */
    public LearningCodelet(String id, String expectationCodeletId, Encoder encoder){
        super();
	this.id = id;
        this.expectationCodeletId = expectationCodeletId;
        this.encoder = encoder;
        
        setName(id);
        encoder.mountEncoderMaps();
    }
    
    
    /*@Override
    public void accessMemoryObjects() {
        this.appraisalMO = this.getInput("APPRAISAL");
        this.weightsMO = this.getOutput("WEIGHTS_RECORD");
        this.weightsInputMO = this.getInput("WEIGHTS_INPUT");
        
        this.preditorWeightsMO = this.getOutput("PREDITOR_WEIGHTS");
        //this.selectionFlagMemory = this.getInput("SELECTION_FLAG");
        
        this.currentPerceptionMO = this.getInput("CURRENT_PERCEPTION_COPY");
        this.predictedSituationMO = this.getInput("PREDICTED_SITUATION");
        this.nextAction = this.getInput("NEXT_ACTION");
        
        this.broadcastedResetMO = this.getBroadcast("RESET_COUNTERS");
        this.deathMemory = this.getInput("DEATH");
    }*/
    
    //Arrays.asList("move","strafe","pitch","turn", "jump","crouch","attack","use");

    @Override
    public void proc() {
        
        //recebe a entrada e a respectiva saida do expectation
        //recebe o proximo estado do current perception e guarda o reforço
        
        

    }
    
    public boolean isInList(final List<double[]> list, final double[] candidate) {
        return list.stream().anyMatch(a -> Arrays.equals(a, candidate));
        //  ^-- or you may want to use .parallelStream() here instead
    }
    
    /*public double[] getEpisode(final List<double[]> list, final double[] element){
        //return list.stream().filter(o -> o.equals(element)).findFirst().get();
        //return list.stream().anyMatch(o -> Arrays.equals(o, element));
        return list.stream().filter(o -> Arrays.equals(o, element)).findFirst().get();
    }
    
    public List<Integer> getIndexes(List<double[]> list, double[] array){
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            double[] entry = list.get(i);
            if(Arrays.equals(entry, array)){
                indexes.add(i);
            }
        }
        return indexes;
    }
    
    public double[] processActionTaken(double[] tempOutput){
        double[] action = new double[tempOutput.length];
        
        int Ind = Doubles.indexOf(tempOutput, Doubles.max(tempOutput));
        
        Arrays.fill(action, 0);
        action[Ind] = 1;
        
        return action;
    }
    
    public float[][] receiveColumn (float[][] first, float[] second, int pos){
        for (int i = 0; i < second.length; i++){
                first[i][pos] = second[i];
        }
        return first;
    }
    
    //official
    public double[][] receiveRowDouble (double[][] first, double[] second, int pos){
        //just for debugging
        //if(Doubles.max(second) == 0){
        //        System.out.println("shit here!");
        //    }
        
        if(pos < first.length){
            for(int k = 0; k < second.length; k++){
                first[pos][k] = second[k];
            }
            return first;
        }
        //else{
        double[][] resultMatrix = new double[first.length +1 + (pos - first.length)][second.length];
        for (int i = 0; i < second.length; i++){
            for(int j = 0; j < first.length; j++){
                resultMatrix[j][i] = first[j][i];
            }
        }
        for(int k = 0; k < second.length; k++){
                resultMatrix[pos][k] = second[k];
            }
        
        return resultMatrix;
        //}
    }
    
    public double[] getNewRecord(String action){
        Map<String, double[]> possibleNewRecords = (Map<String, double[]>)weightsInputMO.getI();
        //boolean blankTest = false;
        double[] newRecord = possibleNewRecords.get(action);
        
        return newRecord;
    }
    
    public void ensureCommand(String action, AbstractObject conf){
        
    }
    
    public double[] processInput(double[] tempInput, double[] tempOutput){
        double[] actionSelected = Arrays.copyOfRange(tempOutput, 15, 33);
        
        tempInput = Doubles.concat(tempInput, actionSelected);
        return tempInput;
    }
    
    public double[] processInput2(double[] tempInput, double[] tempOutput){
        //double[] nextPos = {tempOutput[9],tempOutput[11],tempOutput[13]};
        double[] nextPos = {tempOutput[9],tempOutput[11]};
        //double [] inputProcessed = Arrays.copyOfRange(tempOutput, 9, 14);
        double [] inputProcessed = Arrays.copyOfRange(tempInput, 33, 42);
        //double [] inputProcessed = Doubles.concat(Arrays.copyOfRange(tempInput, 9, 14),Arrays.copyOfRange(tempInput, 33, 42));
        //tempInput = Doubles.concat(tempInput, actionSelected);
        return inputProcessed;
    }
    
    public boolean criteria(double[] newRecord, double[] perceptionEncoded){
        boolean result = false;
        double[] actionArray = new double[8];
        
        int index = 0;
        for(int i = newRecord.length-8; i < newRecord.length;i++){
            actionArray[index] = newRecord[i];
            index++;
        }
        
        index=0;
        for(int i = 15; i < 15+actionArray.length; i++){
            if(((perceptionEncoded[i] == actionArray[index]) && (perceptionEncoded[i] != -2.0)) || ((actionArray[0] == -2.0)&&(actionArray[3] == -2.0))){
                result = true;
            }
            index++;
        }
         
        return result;
    }
    
    public void writeSet(List<double[]> listSet,String FileName, String category) throws IOException {		
	FileWriter FileOutput = new FileWriter(FileName+"_" +category);
        PrintWriter writer = new PrintWriter(FileOutput);
        
        for(double[] set : listSet){
            for (int i = 0; i < set.length; i++){
                writer.print(set[i] + " ");
            }    
                writer.println();
                writer.println();
                writer.println();
                writer.println(); 
        }
                
        writer.close();
    }/*
    
        
    
    /*public List<double[]> getRecordedInputs(){
        return recordedInputsActionCondition;
    }
    
    public List<double[]> getRecordedOutputs(){
        return recordedActualOutputs;
    }
    
    public List<double[]> getAllDataTogether(){
        return allDataTogether;
    }*/
    
    public boolean containsName(final List<AbstractObject> list, final String name){
        return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
    
    public AbstractObject getAbstractObjectStruct(final AbstractObject list, final String name){
        return list.getCompositeParts().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    public Property getPropertyStruct(final AbstractObject list, final String name){
        return list.getProperties().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
    public QualityDimension getQDStruct(final Property list, final String name){
        return list.getQualityDimensions().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
    /*public  double[] receives(double[][] original, int row){
        double[] result = new double[original[0].length];
        
        for(int i = 0; i < result.length; i++){
            result[i] = original[row][i];
        }
        return result;
    }*/
    
    
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
	 * @return the expectationCodeletId
	 */
	public String getLearningCodeletId() {
		return expectationCodeletId;
	}


	/**
	 * @param expectationCodeletId the expectationCodeletId to set
	 */
	public void setExpectationCodeletId(String expectationCodeletId) {
		this.expectationCodeletId = expectationCodeletId;
	}
        
        
}