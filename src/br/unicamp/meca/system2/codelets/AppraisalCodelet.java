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
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.motivational.Appraisal;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;
import br.unicamp.meca.system1.codelets.S1To2AttentionCodelet;
import br.unicamp.meca.util.AbstractObjectPair;
import java.util.List;


/**
 *
 * @author wander
 */
public class AppraisalCodelet extends Codelet{
    private Memory outputAppraisalMemory;
    private Memory predictedSituationMO;
    private Memory currentPerceptionMemory;
    private Appraisal appraisal;
    
    private AbstractObject predicted;
    private AbstractObject lastActual;
    private AbstractObject actual;
    private AbstractObjectPair evaluation;
    private S1To2AttentionCodelet attention;
    
    private String Id;
    
    public AppraisalCodelet(S1To2AttentionCodelet attention, String id){
        this.Id = id;
    }
    
    
    @Override
    public void accessMemoryObjects(){
        this.currentPerceptionMemory = this.getInput("CURRENT_PERCEPTION");
        this.predictedSituationMO = this.getInput("PREDICTED_SITUATION");
        this.outputAppraisalMemory = this.getOutput("OUTPUT_APPRAISAL_MEMORY");
    }
    
    
    @Override
    public void proc() {
        predicted = (AbstractObject)predictedSituationMO.getI();
        actual = (AbstractObject)currentPerceptionMemory.getI();
        
        if((double)getPropertyStruct(getAbstractObjectStruct(actual,"self"),"TotalTime").getQualityDimensions().get(0).getValue() > (double)getPropertyStruct(getAbstractObjectStruct(lastActual,"self"),"TotalTime").getQualityDimensions().get(0).getValue()){
            appraisal = appraisalGeneration(predicted, actual);
            evaluation.setBefore(predicted);
            evaluation.setAfter(actual);
            evaluation.setAppraisal(appraisal);
        }
        
        lastActual = actual;
        
        outputAppraisalMemory.setI(evaluation);
    }
    
    public Appraisal appraisalGeneration(AbstractObject predicted, AbstractObject actual){
        
        if(equals(actual,predicted)){
            Appraisal app = new Appraisal("similarity","high",80);
            return app;
        }
        else return new Appraisal("similarity","low",0);
        
    }


    @Override
    public void calculateActivation() {
    }

   /* public Memory getOutputAppraisalMO() {
        return outputAppraisalMemory;
    }

    public void setOutputAppraisalMO(Memory outputAppraisalMO) {
        this.outputAppraisalMemory = outputAppraisalMO;
    }

    public AbstractObject getInputAbstractObject() {
        return inputAbstractObject;
    }

    public void setInputAbstractObject(AbstractObject inputAbstractObject) {
        this.inputAbstractObject = inputAbstractObject;
    }

    public Appraisal getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
    }

    public Memory getInputAbstractObjectMO() {
        return inputAbstractObjectMO;
    }

    public void setInputAbstractObjectMO(Memory inputAbstractObjectMO) {
        this.inputAbstractObjectMO = inputAbstractObjectMO;
    }*/
    
    public boolean equals(AbstractObject currentPerception, AbstractObject predictedSituation){
        boolean isSimilar = false; //resposta final
        boolean thereIsParts = false;
        
        double propSimilarity;
        double partSimilarity;
        
        double qDThreshold = 0.8; //chutei. rever melhor
        double propThreshold = 0.8; //chutei. rever melhor
        double partThreshold = 1.0; //chutei. rever melhor
        
       
        boolean[] partSimilar = new boolean[predictedSituation.getCompositeParts().size()];
         //vetor de booleans q representa quantos qDs sao iguais
        boolean[] propSimilar = new boolean[predictedSituation.getProperties().size()]; //vetor de booleans q representa quantas Properties sao iguais
        
        if(!predictedSituation.getCompositeParts().isEmpty()){
            thereIsParts = true;
            int partCount = 0;
            for(AbstractObject part : predictedSituation.getCompositeParts()){
            //primeiro loop tera 3 partes
                partSimilar[partCount] = equals(getAbstractObjectStruct(currentPerception,part.getName()),part);
                partCount++;
            }
        }
        //pra cada qualityDimension de cada propriedade
        int prp = 0;
        for(Property prop : predictedSituation.getProperties()){
            
            Property objPropTemp = getPropertyStruct(currentPerception, prop.getName());
            boolean[] qDSimilar = new boolean[prop.getQualityDimensions().size()];
            
            int i = 0;
            for(QualityDimension qD : prop.getQualityDimensions()){
                
                QualityDimension objQDTemp = getQDStruct(objPropTemp, qD.getName());
                if(qD.isString()){
                    String qDValue = (String)qD.getValue();
                    String objQDValue = (String)objQDTemp.getValue();
                    if(qDValue.equals(objQDValue)){
                        qDSimilar[i] = true;
                    }
                    else{
                        qDSimilar[i] = false;
                    }
                }
                else if(qD.isBoolean()){
                    boolean qDValue = (boolean)qD.getValue();
                    boolean natQDValue = (boolean)objQDTemp.getValue();
                    if(qDValue == natQDValue){
                        qDSimilar[i] = true;
                    }
                    else{
                        qDSimilar[i] = false;
                    }
                }
                else if(qD.isDouble()){
                    double qDValue = (double)qD.getValue();
                    double natQDValue = (double)objQDTemp.getValue();
                    if(similiarDispersion(qDValue, natQDValue,attention.checkStd(qD.getName()))){
                        qDSimilar[i] = true;
                    }
                    else{
                        qDSimilar[i] = false;
                    }
                }
                i++;
                
                //contar quantos qDsimilar sao true e se passar do limiar definir o propSimiliar como true
            }
            int count = 0;
            for(int index=0; index < qDSimilar.length; index++){
                if(qDSimilar[index]){
                    count++;
                }
            }
            if((count/qDSimilar.length) >= qDThreshold){
                propSimilar[prp] = true;
            }
            else{
                propSimilar[prp] = false;
            }            
            prp++;
        }
        int countP = 0;
        for(int index=0; index < propSimilar.length; index++){
            if(propSimilar[index]){
                countP++;
            }
        }
        propSimilarity = countP/propSimilar.length;
        
        countP = 0;
        for(int index=0; index < partSimilar.length; index++){
            if(partSimilar[index]){
                countP++;
            }
        }
        partSimilarity = countP/partSimilar.length;
        
        //contar similaridade de partes e propriedades
        if((thereIsParts && (partSimilarity >= partThreshold) && (propSimilarity >= propThreshold)) || (!thereIsParts && (propSimilarity >= propThreshold))){
            isSimilar = true;
        }
     
        return isSimilar;
    }
    
    
    
    //checa se os valores estao dentro de um range um do outro
    public boolean similiarDispersion(double actual, double comparative, double std){
        boolean similar = false;
        
        if((actual >= comparative - std) && (actual <= comparative + std)){
            similar = true;
        }
        return similar;
    }
    
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
    
    
}
