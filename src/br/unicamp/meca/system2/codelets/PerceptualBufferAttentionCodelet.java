/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP                                        *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     W. Gibaut, and R. R. Gudwin                                             * 
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.representation.owrl.AbstractObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author W. Gibaut
 */
public abstract class PerceptualBufferAttentionCodelet extends AttentionCodelet{    
    
    //protected String id;
    
    protected Memory currentPerceptionMemory;
    
    protected Memory perceptualBufferMemory;
    /** We need a reference to the SelectionCodelet which will read the outputs of this ExpectationCodelet*/
	protected String episodicAttentionCodeletId;
        
    /** We need a reference to the SelectionCodelet which will read the outputs of this ExpectationCodelet*/
	protected String s1to2AttentionCodeletId;
    
    //private MemoryObject perceptualBufferMO;
    protected List<AbstractObject> bufferList;
    private AbstractObject currentPerception;
    private final int maxSize;
    private AbstractObject oldPerception;
    
    //private double lastPerceptionReceivedID = 0;
    
    public PerceptualBufferAttentionCodelet(String id, String episodicAttentionCodeletId, String s1to2AttentionCodeletId, int maxSize){
        this.id = id;
        this.episodicAttentionCodeletId = episodicAttentionCodeletId;
        this.s1to2AttentionCodeletId = s1to2AttentionCodeletId;
        this.maxSize = maxSize;
        setName(id);
        
        bufferList = new ArrayList<>();
    }
    

    @Override
    public void proc() {
        // Este codelet captura o current perception e o joga para um "buffer" onde agrupamentos signifcativos serão feitos
        //if((currentPerceptionMO.getEvaluation() > lastPerceptionReceivedID)){
            
           //Pegar o buffer 
           addPerceptionToBuffer();
           //dar set no buffer

    }
    
    public void addPerceptionToBuffer(){
        currentPerception = (AbstractObject)currentPerceptionMemory.getI();
        if((currentPerception != null && !currentPerception.equals(oldPerception)) || (currentPerception != null && oldPerception == null)){
            
            
                bufferList.add(currentPerception);
            
                if(bufferList.size() > maxSize){
                //remove os elementos mais antigos da lista
                    for(int i = (bufferList.size() - maxSize); i>0; i--){
                        bufferList.remove(0);
                    }
                }
                oldPerception =  currentPerception;
            }
    }
        
    @Override
    public void calculateActivation() {
    }
        
}
