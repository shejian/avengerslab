package com.avengers.appwakanda.ui.common

import java.io.Serializable

/**
 * 用来对主要的操作数据做标记
 */
interface IBeanResponse : Serializable

/**
 * 请求参数必须实现这个接口，用于标记是请求参数
 */
interface IReqParam : Serializable