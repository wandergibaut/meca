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
package br.unicamp.meca.system1.codelets;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.cst.motivational.Drive;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;

/**
 * This class represents a MECA Attention Codelet in System 1. Attention
 * codelets are specialized kinds of codelets which will work as salience
 * detectors for objects, situations, events or episodes happening at the
 * environment which might be important for defining an action strategy, or
 * behavior. Attention Codelets track percepts in order to detect special
 * situations and send information upstream to System 2. These Attention
 * Codelets are responsible for generating the Current Perception at the Working
 * Memory, where a selected subset of the Perception Memory is made available
 * for System 2 subsystems in a representation suitable to be processed within
 * System 2.
 * 
 * 
 * @author WAAAAAANNDERRRRR
 * @author A. L. O. Paraense
 * @see Codelet
 *
 */
public abstract class S1To2AttentionCodelet extends Codelet {

	private String id;
	private Memory inputPerceptsMO;
	private Memory outputFilteredPerceptsMO;

	private List<AbstractObject> inputPercepts;
	private AbstractObject outputFilteredPercepts;
	private List<String> perceptualCodeletsIds;

	/**
	 * Creates a MECA System 1 Attention Codelet.
	 * 
	 * @param id
	 *            the id of the Attention Codelet. Must be unique per Attention
	 *            Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs will
	 *            be read by this Attention Codelet.
	 */
	public S1To2AttentionCodelet(String id, ArrayList<String> perceptualCodeletsIds) {
		setName(id);
		setId(id);
		setPerceptualCodeletsIds(perceptualCodeletsIds);
		setInputPercepts(new ArrayList<AbstractObject>());
	}

	@Override
	public void accessMemoryObjects() {

		if (getInputPercepts() == null || getInputPercepts().isEmpty()) {
			for (Memory perceptualMO : this.getInputs()) {
				getInputPercepts().add((AbstractObject) perceptualMO.getI());
			}
		}

		if (getOutputFilteredPerceptsMO() == null) {
			setOutputFilteredPerceptsMO(getOutput(getId()));
		}

	}

	@Override
	public void calculateActivation() {
		try {
			setActivation(0);
		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void proc() {
		setOutputFilteredPercepts(generateFilteredPercepts(getInputPercepts()));
		getOutputFilteredPerceptsMO().setI(getOutputFilteredPercepts());
	}

	/**
	 * Generates a filtered percept as an AbstractObject.
	 * 
	 * @param inputPercepts
	 *            the list of input percepts.
	 * @return filtered percept as an AbstractObject.
	 */
	public abstract AbstractObject generateFilteredPercepts(List<AbstractObject> inputPercepts);

	/**
	 * Gets the input Percept as a Memory Object.
	 * 
	 * @return the inputPerceptsMO
	 */
	public Memory getInputPerceptsMO() {
		return inputPerceptsMO;
	}

	/**
	 * Sets the input Percept as a Memory Object.
	 * 
	 * @param inputPerceptsMO
	 *            the input Percept as a Memory Object.
	 */
	public void setInputPerceptsMO(Memory inputPerceptsMO) {
		this.inputPerceptsMO = inputPerceptsMO;
	}

	/**
	 * Gets the output filtered percept and a Memory Object.
	 * 
	 * @return the outputFilteredPerceptsMO
	 */
	public Memory getOutputFilteredPerceptsMO() {
		return outputFilteredPerceptsMO;
	}

	/**
	 * Sets the output filtered percept and a Memory Object.
	 * 
	 * @param outputFilteredPerceptsMO
	 *            the outputFilteredPerceptsMO
	 */
	public void setOutputFilteredPerceptsMO(Memory outputFilteredPerceptsMO) {
		this.outputFilteredPerceptsMO = outputFilteredPerceptsMO;
	}

	/**
	 * Gets the list of input percepts.
	 * 
	 * @return the list of input percepts.
	 */
	public List<AbstractObject> getInputPercepts() {
		return inputPercepts;
	}

	/**
	 * Sets the list of input percepts.
	 * 
	 * @param inputPercepts
	 *            the list of input percepts.
	 */
	public void setInputPercepts(List<AbstractObject> inputPercepts) {
		this.inputPercepts = inputPercepts;
	}

	/**
	 * Gets the output filtered percepts.
	 * 
	 * @return the output filtered percepts.
	 */
	public AbstractObject getOutputFilteredPercepts() {
		return outputFilteredPercepts;
	}

	/**
	 * Sets the output filtered percepts.
	 * 
	 * @param outputFilteredPercepts
	 *            the output filtered percepts.
	 */
	public void setOutputFilteredPercepts(AbstractObject outputFilteredPercepts) {
		this.outputFilteredPercepts = outputFilteredPercepts;
	}

	/**
	 * Gets the id of this Attention Codelet.
	 * 
	 * @return the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Attention Codelet.
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * Gets the list of ids of the Perceptual Codelets whose outputs will be
	 * read by this Attention Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public List<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of ids of the Perceptual Codelets whose outputs will be
	 * read by this Attention Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set.
	 */
	public void setPerceptualCodeletsIds(List<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}
        
        
        //Seção adicional a ser revisada e posta no formato meca
        
        public boolean equals(AbstractObject obj, AbstractObject nat_ignore){
            return this.equals(obj, nat_ignore, 0.8, 0.8, 1.0);
        }
        
        public boolean equals(AbstractObject obj, AbstractObject nat_ignore, double qDThreshold, double propThreshold, double partThreshold){
        boolean isSimilar = false; //resposta final
        boolean thereIsParts = false;
        
        double propSimilarity;
        double partSimilarity;
        

        boolean[] partSimilar = new boolean[nat_ignore.getCompositeParts().size()];
         //vetor de booleans q representa quantos qDs sao iguais
        boolean[] propSimilar = new boolean[nat_ignore.getProperties().size()]; //vetor de booleans q representa quantas Properties sao iguais
        
        if(!nat_ignore.getCompositeParts().isEmpty()){
            thereIsParts = true;
            int partCount = 0;
            for(AbstractObject part : nat_ignore.getCompositeParts()){
            //primeiro loop tera 3 partes
                partSimilar[partCount] = equals(getAbstractObjectStruct(obj,part.getName()),part);
                partCount++;
            }
        }
        //pra cada qualityDimension de cada propriedade
        int prp = 0;
        for(Property prop : nat_ignore.getProperties()){
            
            Property objPropTemp = getPropertyStruct(obj, prop.getName());
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
                    if(similiarDispersion(qDValue, natQDValue,checkStd(qD.getName()))){
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
    
    public AbstractObject fromDriveToOWRL(Drive drive){
        List<Property> properties = new ArrayList();
        Property driveName = new Property("name",new QualityDimension("name",drive.getName()));
        Property driveLevel = new Property("level",new QualityDimension("level",drive.getLevel()));
        Property drivePriority = new Property("priority",new QualityDimension("priority",drive.getPriority()));
        Property driveUrgency = new Property("urgencyThreshold",new QualityDimension("urgencyThreshold",drive.getUrgencyThreshold()));
        
        properties.add(driveName);
        properties.add(driveLevel);
        properties.add(drivePriority);
        properties.add(driveUrgency);
        
        AbstractObject driveOWRL = new AbstractObject(drive.getName()+"Drive", properties);
        
        return driveOWRL;
    }
    
    
    //checa se os valores estao dentro de um range um do outro
    public boolean similiarDispersion(double actual, double comparative, double std){
        boolean similar = false;
        
        if((actual >= comparative - std) && (actual <= comparative + std)){
            similar = true;
        }
        return similar;
    }
    
    //funcao q diz o q eh um "desvio padrao" pra cada tipo de valor. obviamente depende da aplicação
    public abstract double checkStd(String name);
    
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
