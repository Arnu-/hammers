/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrImage {

    /**
     * 二维码的内容(非空)
     */
    private String qrCodeContent;

    /**
     * 二维码的宽度(非空)
     */
    private Integer qrCodeWidth;

    /**
     * 二维码的高度(非空)
     */
    private Integer qrCodeHeight;

    /**
     * 二维码内嵌图片的文件路径(为空则表示:二维码中间不嵌套图片)
     */
    private String embeddedImgFilePath;

    /**
     * 文字的大小(即:正方形文字的长度、宽度)(非空)
     */
    private Integer wordSize;

    /**
     * 文字的内容(非空)
     */
    private String wordContent;

    /**
     * 二维码文件的输出路径(非空)
     */
    private String qrCodeFileOutputPath;

}
