
/**
 * Project Name: sample-app-kickstart-anno-mybatisPlus
 * File Name: GraphqlUtil.java
 * @date 2023年7月17日 下午6:04:43
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.utils;

import java.util.function.Supplier;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;

/**
 * TODO <br/>
 * @date 2023年7月17日 下午6:04:43
 * @author jiangpq
 * @version
 */
public class GraphqlUtil {
	public static <T> T runTrue(boolean isTrue, Supplier<T> run) {
		return runThen(isTrue, run, () -> null);
	}
	public static <T> T runThen(boolean isTrue, Supplier<T> trueRun, Supplier<T> falseRun) {
		return isTrue ? trueRun.get() : falseRun.get();
	}
	public static <T> T runTrue(DataFetchingEnvironment env, String param, Supplier<T> run) {
    	return runThen(env, param, run, () -> null);
	}
	public static <T> T runThen(DataFetchingEnvironment env, String param, Supplier<T> trueRun, Supplier<T> falseRun) {
    	DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();
    	return runThen(selectionSet.contains(param), trueRun, falseRun);
	}
}

 