/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

/**
 *
 * @author wander
 */
public class NaturalizationCodelet extends Codelet{
    private Memory appraisalMO;
    private Memory naturalizationMO;
    
    @Override
    public void accessMemoryObjects() {
        this.appraisalMO = this.getInput("OUTPUT_APPRAISAL_MEMORY");
        this.naturalizationMO = this.getOutput("NATURALIZATION");
    }

    @Override
    public void proc() {
        //a ideia eh compilar em tempo de  execucao behaviors q possam substituir os processos mais complexos
    }
        
    @Override
    public void calculateActivation() {
    }
}
