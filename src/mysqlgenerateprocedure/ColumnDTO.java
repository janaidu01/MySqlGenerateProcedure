/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlgenerateprocedure;

/**
 *
 * @author Minh Nhat
 */
public class ColumnDTO {
    private String COLUMN_NAME = null;
    private String DATA_TYPE = null;
    private String CHARACTER_MAXIMUM_LENGTH = null;
    
    public ColumnDTO(){
        
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getCHARACTER_MAXIMUM_LENGTH() {
        return CHARACTER_MAXIMUM_LENGTH;
    }

    public void setCHARACTER_MAXIMUM_LENGTH(String CHARACTER_MAXIMUM_LENGTH) {
        this.CHARACTER_MAXIMUM_LENGTH = CHARACTER_MAXIMUM_LENGTH;
    }
    
    
}
