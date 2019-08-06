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

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.models.Episode;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author W. Gibaut
 */
public abstract class EpisodicAttentionCodelet extends AttentionCodelet{
    
    //protected String id;
    
    protected String perceptualBufferCodeletId;
    
    protected String episodicLearningCodeletId;
    
    protected Memory perceptualBufferMemory;
    protected Memory episodicBufferMemory;
    
    protected List<INDArray> perceptionBufferList = new ArrayList<>();
    protected List<Episode> episodicBufferList = new ArrayList<>();
    private final int maxSize;

    public EpisodicAttentionCodelet(String id, String perceptualBufferCodeletId, String episodicLearningCodeletId, int maxSize){
        this.setId(id);
        this.perceptualBufferCodeletId = perceptualBufferCodeletId;
        this.episodicLearningCodeletId = episodicLearningCodeletId;
        this.maxSize = maxSize;
        
        setName(id);
        //perceptionBufferList = new ArrayList<>();
        episodicBufferList = new ArrayList<>();
        
    }
    
    @Override
    public void accessMemoryObjects() {
        //erro aqui. Definir padrão pra esses ids
		if(episodicBufferMemory==null)
			episodicBufferMemory = this.getOutput(this.id);
                
                if(perceptualBufferMemory==null && perceptualBufferCodeletId!=null)
			perceptualBufferMemory = this.getInput(perceptualBufferCodeletId);

    }

    @Override
    public void proc() {
        if(episodicBufferMemory != null){
            perceptionBufferList = (List<INDArray>) episodicBufferMemory.getI();
            //pega as listas
            perceptionBundleMethod();
                    //da set nas memories
        }
    }
    
    public abstract void perceptionBundleMethod();
    
    public void perceptionBundleMethodDefault(){
            //esse valor representa a diferença de tamanho entre o tamanho anterior e o novo tamanho do buffer
            //ou seja, quantos perceptos foram capturados desde então
                //incrementalSize = perceptionBufferList.size()- incrementalSize; 
            
                //for(int i=incrementalSize-1; i>1;i--){
                //cria pares de configurações (ou seja, uma ação) pro EpBuffer
                if(perceptionBufferList.size() >1){
                    episodicBufferList.add(new Episode(perceptionBufferList.get(perceptionBufferList.size()-2),perceptionBufferList.get(perceptionBufferList.size()-1)));
                }
                    
                //}
                if(episodicBufferList.size() > maxSize){
                //remove os elementos mais antigos da lista
                    for(int i = (episodicBufferList.size() - maxSize); i>0; i--){
                        episodicBufferList.remove(0);
                    }
                }
    }
    
    public void setPerceptualBufferMemory(Memory perceptualBufferMemory){
        this.perceptualBufferMemory = perceptualBufferMemory;
    }
    
    public Memory getPerceptualBufferMemory(){
        return this.perceptualBufferMemory;
    }
    
    public void setEpisodicBufferMemory(Memory episodicBufferMemory){
        this.episodicBufferMemory = episodicBufferMemory;
    }
    
    public Memory getEpisodicBufferMemory(){
        return this.episodicBufferMemory;
    }
        
}
