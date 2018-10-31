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
package br.unicamp.meca.system1.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

/**
 *
 * @author W. Gibaut
 */
public class AdaptationCodelet extends Codelet{
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
