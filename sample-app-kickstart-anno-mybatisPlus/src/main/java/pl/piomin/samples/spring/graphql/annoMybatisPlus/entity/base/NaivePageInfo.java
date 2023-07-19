
/**
 * Project Name: sample-app-kickstart-anno-mybatisPlus
 * File Name: PageInfo.java
 * @date 2023年7月18日 上午10:37:31
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO <br/>
 * @date 2023年7月18日 上午10:37:31
 * @author jiangpq
 * @version
 */
//@GraphQLName("NaivePageInfo")
@Data
@NoArgsConstructor
public class NaivePageInfo<T> {
/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	
	//	@ApiModelProperty(value = "当前页", name = "pageNum")
    @GraphQLField
    private long pageNum = 1;
    
//    @ApiModelProperty(value = "每页数", name = "pageSize")
    @GraphQLField
    private long pageSize = 20;
    
//    @ApiModelProperty(value = "总页数", name = "pages")
    @GraphQLField
    private long pages;
    
//    @ApiModelProperty(value = "总记录条数", name = "total")
    @GraphQLField
    private long total;
    
//    @ApiModelProperty(value = "数据列表", name = "list")
    public List<T> datas;
    
    public NaivePageInfo(Page<T> page) {
        this.datas = page.getRecords();
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.pages = page.getPages();
        this.total = page.getTotal();
    }

}

 