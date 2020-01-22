/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.util;

import br.unicamp.cst.representation.owrl.AbstractObject;

import java.util.List;

/**
 *
 * @author wander
 */
public abstract class Encoder {
    
    
    public abstract List<float[]> encodeConfiguration(AbstractObject conf);
    
    public abstract double[][] encodeConfigurationDouble(AbstractObject conf);
    
    public abstract double[] encodeActionDouble(String action);
    
    public abstract void mountEncoderMaps();
    
    public abstract double[] encodeWithoutNormalization(AbstractObject conf);
}
