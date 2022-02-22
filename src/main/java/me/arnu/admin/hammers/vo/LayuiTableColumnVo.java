/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/2/20                                
*/
package me.arnu.admin.hammers.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于定义表格显示内容的vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LayuiTableColumnVo {
    //  , {field: 'lastYearAnnualVacationBalance', width: 100, title: '去年结余年假', align: 'center'}
    private String field;
    private int width;
    private String title;
    private String align;
    private String fixed;
}
