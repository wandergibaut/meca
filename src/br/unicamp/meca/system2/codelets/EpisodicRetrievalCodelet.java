/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;
import br.unicamp.meca.util.AbstractObjectPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wander
 */
public abstract class EpisodicRetrievalCodelet extends Codelet{
    
    protected String id;
    
    protected String episodicLearningCodeletId;
    protected String s1To2AttentionCodeletId;
    
    private Memory cueMemoryMO;
    private Memory episodicMemory; 
    private Memory episodicRecallMemory;
    
    
    private List<Memory> episodicMemoryMOs; 
    //soh mesmo pra ter o metodo checkStd correto
    //private S1To2AttentionCodelet_apagar attention;
    
    //private double lastPerceptionReceivedID = 0;
    
    public EpisodicRetrievalCodelet(String id, String episodicLearningCodeletId, String s1To2AttentionCodeletId){
        this.id = id;
        this.episodicLearningCodeletId = episodicLearningCodeletId;
        this.s1To2AttentionCodeletId = s1To2AttentionCodeletId;
        setName(id);
        
        
    }
    
    @Override
    public void accessMemoryObjects() {

        
        
        if(episodicMemory==null && episodicLearningCodeletId!=null)
			this.episodicMemory = this.getInput(episodicLearningCodeletId);
        
        if(cueMemoryMO==null)
			this.cueMemoryMO = this.getInput(s1To2AttentionCodeletId);
                
        if(episodicRecallMemory==null && id!=null)
                	this.episodicRecallMemory = this.getOutput(id);
    }

    @Override
    public void proc() {
        List<AbstractObjectPair> recallEpisodes = new ArrayList<>();
        //episodicMemory.
        episodicMemoryMOs = (List<Memory>)episodicMemory.getI();
        
        AbstractObject currentPerceptionToSearch = (AbstractObject)cueMemoryMO.getI();
        if(currentPerceptionToSearch != null && episodicMemoryMOs != null && !episodicMemoryMOs.isEmpty()){
            
                
                for(int i=0; i<episodicMemoryMOs.size(); i++){
                    Memory epMem = episodicMemoryMOs.get(i);
                    AbstractObjectPair episode = (AbstractObjectPair)epMem.getI();
                    if(equals(currentPerceptionToSearch, episode)){
                    //if(equals(currentPerceptionToSearch, episode.getBefore())){
                        recallEpisodes.add(episode);
                    }
                }
            episodicRecallMemory.setI(recallEpisodes);

        }
    
    }
    
    public abstract boolean equals(AbstractObject currentPerceptionToSearch, AbstractObjectPair episode);
    
    public boolean specialEquals(AbstractObject cueMemoryConf, AbstractObject epMemBefore){
        boolean isSimilar = true; //resposta final
        
        List<Property> cueProperties = cueMemoryConf.getCompositeParts().get(1).getCompositeParts().get(0).getProperties();
        List<Property> epProperties = epMemBefore.getCompositeParts().get(1).getCompositeParts().get(0).getProperties();
        
        for(int i=0; i<cueProperties.size();i++){
            String cuePropValue = (String)cueProperties.get(i).getQualityDimensions().get(0).getValue();
            String epMemPropValue = (String)epProperties.get(i).getQualityDimensions().get(0).getValue();
            //isSimilar = true;
            if(!cuePropValue.equals(epMemPropValue)){
                isSimilar = false;
            }
        }
        
        return isSimilar;
    }
    
    public abstract double checkStd(String name);
    
    public boolean equals(AbstractObject cueMemoryConf, AbstractObject epMemBefore){
        boolean isSimilar = false; //resposta final
        boolean thereIsParts = false;
        
        double propSimilarity;
        double partSimilarity;
        
        double qDThreshold = 0.6; //chutei. rever melhor
        double propThreshold = 0.6; //chutei. rever melhor
        double partThreshold = 1.0; //chutei. rever melhor
        
       
        boolean[] partSimilar = new boolean[epMemBefore.getCompositeParts().size()];
         //vetor de booleans q representa quantos qDs sao iguais
        boolean[] propSimilar = new boolean[epMemBefore.getProperties().size()]; //vetor de booleans q representa quantas Properties sao iguais
        
        if(!epMemBefore.getCompositeParts().isEmpty()){
            thereIsParts = true;
            int partCount = 0;
            for(AbstractObject part : epMemBefore.getCompositeParts()){
            //primeiro loop tera 3 partes
                partSimilar[partCount] = equals(getAbstractObjectStruct(cueMemoryConf,part.getName()),part);
                partCount++;
            }
        }
        //pra cada qualityDimension de cada propriedade
        int prp = 0;
        for(Property prop : epMemBefore.getProperties()){
            if(epMemBefore.getProperties().size() != cueMemoryConf.getProperties().size()){
            //if(!containsPropertyStruct(cueMemoryConf, prop.getName())){
                propSimilar[prp] = true;
                prp++;
                continue;
            }
            else if(!containsPropertyStruct(cueMemoryConf, prop.getName())){
                propSimilar[prp] = false;
                prp++;
                continue;
            }
            Property objPropTemp = getPropertyStruct(cueMemoryConf, prop.getName());
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
            if((((double)count)/qDSimilar.length) >= qDThreshold){
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
        
        if(propSimilar.length != 0){
            propSimilarity = ((double)countP)/propSimilar.length;
        }
        else{
            propSimilarity = 1;
        }
        
        
        
        countP = 0;
        for(int index=0; index < partSimilar.length; index++){
            if(partSimilar[index]){
                countP++;
            }
        }
        if(partSimilar.length != 0){
            partSimilarity = ((double)countP)/partSimilar.length;
        }
        else{
            partSimilarity = 1;
        }
        
        
        //contar similaridade de partes e propriedades
        if((thereIsParts && (partSimilarity >= partThreshold) && (propSimilarity >= propThreshold)) || (!thereIsParts && (propSimilarity >= propThreshold))){
            isSimilar = true;
        }
     
        return isSimilar;
    }
    
    public boolean similiarDispersion(double actual, double comparative, double std){
        boolean similar = false;
        
        if((actual >= comparative - std) && (actual <= comparative + std)){
            similar = true;
        }
        return similar;
    }
    
    //funcao q diz o q eh um "desvio padrao" pra cada tipo de valor. obviamente depende da aplicação
    
    public boolean containsName(final List<AbstractObject> list, final String name){
        return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
    
    public AbstractObject getAbstractObjectStruct(final AbstractObject list, final String name){
        return list.getCompositeParts().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    public Property getPropertyStruct(final AbstractObject list, final String name){
        return list.getProperties().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    public boolean containsPropertyStruct(final AbstractObject list, final String name){
        return list.getProperties().stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
    
    public QualityDimension getQDStruct(final Property list, final String name){
        return list.getQualityDimensions().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
}