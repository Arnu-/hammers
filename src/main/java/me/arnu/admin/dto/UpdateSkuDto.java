/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.dto;

import lombok.Data;

import java.util.List;

/**
 * 更新商品SKU设置Dto
 */
@Data
public class UpdateSkuDto {

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * SKU列表
     */
    private List<ProductSkuListDto> skuList;

}
