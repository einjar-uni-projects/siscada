 /* Universidad Carlos III
 * Campus de Leganes
 * Ingenieria Informatica
 * ASIGNATURA: Sistemas Informaticos
*/
/**
 *
 * @author Miguel Degayón Cortés, 100033447
 */
public enum MailBoxEnum {

    AUTO1,
    AUTO2,
    AUTO3,
    ROBOT1,
    ROBOT2,
    MASTER,
    SCADA;

    static final String[] _queues = {"S1", "S2", "S3", "R1", "R2", "SCADA",
                        "M-S1", "M-S2", "M-S3", "M-R1", "M-R2", "M-S2",
                        "M-SCADA", "K-S1", "K-S2", "K-S3", "K-R1", "K-R2",
                        "K-S2", "K-SCADA"};


    public static String getOutputQueueName(MailBoxEnum nodoSCADA){

        String retorno = null;

        if (nodoSCADA.equals(AUTO1)){
            retorno = "S1";
            }else if(nodoSCADA.equals(AUTO2)){
                retorno = "S2";
                }else if(nodoSCADA.equals(AUTO3)){
                    retorno = "S3";
                    }else if(nodoSCADA.equals(ROBOT1)){
                        retorno = "R1";
                        }else if(nodoSCADA.equals(ROBOT2)){
                            retorno = "R2";
                            }else if(nodoSCADA.equals(SCADA)){
                                retorno = "SCADA";
                            }
        return retorno;
    }

    public static String getInputQueueName(MailBoxEnum nodoSCADA){

        String retorno = null;

        if (nodoSCADA.equals(AUTO1)){
            retorno = "M-S1";
            }else if(nodoSCADA.equals(AUTO2)){
                retorno = "M-S2";
                }else if(nodoSCADA.equals(AUTO3)){
                    retorno = "M-S3";
                    }else if(nodoSCADA.equals(ROBOT1)){
                        retorno = "M-R1";
                        }else if(nodoSCADA.equals(ROBOT2)){
                            retorno = "M-R2";
                            }else if(nodoSCADA.equals(SCADA)){
                                retorno = "M-SCADA";
                            }
        return retorno;
    }

    public static String getMasterInputQueueName(MailBoxEnum node) {
        String retorno = null;

        if (node.equals(AUTO1)){
            retorno = "S1";
            }else if(node.equals(AUTO2)){
                retorno = "S2";
                }else if(node.equals(AUTO3)){
                    retorno = "S3";
                    }else if(node.equals(ROBOT1)){
                        retorno = "R1";
                        }else if(node.equals(ROBOT2)){
                            retorno = "R2";
                            }else if(node.equals(SCADA)){
                                retorno = "SCADA";
                            }
        return retorno;
    }

    public static String getMasterOutputQueueName(MailBoxEnum node) {

        String retorno = null;

        if (node.equals(AUTO1)){
            retorno = "M-S1";
            }else if(node.equals(AUTO2)){
                retorno = "M-S2";
                }else if(node.equals(AUTO3)){
                    retorno = "M-S3";
                    }else if(node.equals(ROBOT1)){
                        retorno = "M-R1";
                        }else if(node.equals(ROBOT2)){
                            retorno = "M-R2";
                            }else if(node.equals(SCADA)){
                                retorno = "M-SCADA";
                            }
        return retorno;
    }

    public static String getQueueName(int index){

        if (index >= 0 && index < _queues.length)
            return _queues[index];
        else return null;
    }

    public static int getQueuesLenght(){
        return _queues.length;
    }

    public static String getKeepAliveQueueName(MailBoxEnum node){

        String retorno = null;

        if (node.equals(AUTO1)){
            retorno = "K-S1";
            }else if(node.equals(AUTO2)){
                retorno = "K-S2";
                }else if(node.equals(AUTO3)){
                    retorno = "K-S3";
                    }else if(node.equals(ROBOT1)){
                        retorno = "K-R1";
                        }else if(node.equals(ROBOT2)){
                            retorno = "K-R2";
                            }else if(node.equals(SCADA)){
                                retorno = "K-SCADA";
                            }
        return retorno;

    }
    

}
