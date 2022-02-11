/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品SKU列表Dto
 */
@Data
public class ProductSkuListDto {

    /**
     * SKU值
     */
    private String attributeValue;

    /**
     * 商品图片
     */
    private String productPic;

    /**
     * 销售价
     */
    private BigDecimal price;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * 体积(m³)
     */
    private BigDecimal volume;

    /**
     * 重量(KG)
     */
    private BigDecimal weight;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

}
