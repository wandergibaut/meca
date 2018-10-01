/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.meca.util.AbstractObjectPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wander
 */
public abstract class EpisodicAttentionCodelet extends AttentionCodelet{
    
    //protected String id;
    
    protected String perceptualBufferCodeletId;
    
    protected String episodicLearningCodeletId;
    
    protected Memory perceptualBufferMemory;
    protected Memory episodicBufferMemory;
    
    private List<AbstractObject> perceptionBufferList;
    private List<AbstractObjectPair> episodicBufferList;
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
        perceptionBufferList = (List<AbstractObject>) episodicBufferMemory.getI();
        //pega as listas
        perceptionBundleMethod();
        
        //da set nas memories
        
    }
    
    public abstract void perceptionBundleMethod();
    
    public void perceptionBundleMethodDefault(){
            //esse valor representa a diferença de tamanho entre o tamanho anterior e o novo tamanho do buffer
            //ou seja, quantos perceptos foram capturados desde então
                //incrementalSize = perceptionBufferList.size()- incrementalSize; 
            
                //for(int i=incrementalSize-1; i>1;i--){
                //cria pares de configurações (ou seja, uma ação) pro EpBuffer
                if(perceptionBufferList.size() >1){
                    episodicBufferList.add(new AbstractObjectPair(perceptionBufferList.get(perceptionBufferList.size()-2),perceptionBufferList.get(perceptionBufferList.size()-1)));
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
