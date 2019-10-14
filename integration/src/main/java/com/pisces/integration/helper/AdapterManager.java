package com.pisces.integration.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.bean.DsHttp;
import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.bean.DsNoSql;
import com.pisces.integration.bean.DsSql;
import com.pisces.integration.bean.DsStream;
import com.pisces.integration.bean.DsTcp;
import com.pisces.integration.enums.HTTP_TYPE;
import com.pisces.integration.enums.LOCALE_FILE_TYPE;
import com.pisces.integration.enums.NOSQL_TYPE;
import com.pisces.integration.enums.SQL_TYPE;
import com.pisces.integration.enums.STREAM_TYPE;
import com.pisces.integration.enums.TCP_TYPE;

public class AdapterManager {
	private static List<AdapterRegister<? extends Enum<?>>> adapters = new ArrayList<AdapterRegister<? extends Enum<?>>>();
	
	private static Map<LOCALE_FILE_TYPE, DataSourceAdapter> localeFileAdapter = new HashMap<LOCALE_FILE_TYPE, DataSourceAdapter>();
	private static Map<SQL_TYPE, DataSourceAdapter> sqlAdapter = new HashMap<SQL_TYPE, DataSourceAdapter>();
	private static Map<NOSQL_TYPE, DataSourceAdapter> noSqlAdapter = new HashMap<NOSQL_TYPE, DataSourceAdapter>();
	private static Map<STREAM_TYPE, DataSourceAdapter> streamAdapter = new HashMap<STREAM_TYPE, DataSourceAdapter>();
	private static Map<HTTP_TYPE, DataSourceAdapter> httpAdapter = new HashMap<HTTP_TYPE, DataSourceAdapter>();
	private static Map<TCP_TYPE, DataSourceAdapter> tcpAdapter = new HashMap<TCP_TYPE, DataSourceAdapter>();
	
	public static DataSourceAdapter getAdapter(DataSource source) {
		DataSourceAdapter adapter = null;
		if (source instanceof DsLocaleFile) {
			DsLocaleFile localeFile = (DsLocaleFile)source;
			if (localeFile.getType() != null) {
				adapter = localeFileAdapter.get(localeFile.getType());
			}
		} else if (source instanceof DsSql) {
			DsSql sql = (DsSql)source;
			if (sql.getType() != null) {
				adapter = sqlAdapter.get(sql.getType());
			}
		} else if (source instanceof DsNoSql) {
			DsNoSql noSql = (DsNoSql)source;
			if (noSql.getType() != null) {
				adapter = noSqlAdapter.get(noSql.getType());
			}
		} else if (source instanceof DsStream) {
			DsStream stream = (DsStream)source;
			if (stream.getType() != null) {
				adapter = streamAdapter.get(stream.getType());
			}
		} else if (source instanceof DsHttp) {
			DsHttp http = (DsHttp)source;
			if (http.getType() != null) {
				adapter = httpAdapter.get(http.getType());
			}
		} else if (source instanceof DsTcp) {
			DsTcp tcp = (DsTcp)source;
			if (tcp.getType() != null) {
				adapter = tcpAdapter.get(tcp.getType());
			}
		} else {
			EntityService<? extends EntityObject> service = ServiceManager.getService(source.getClass());
			if (service != null) {
				adapter = (DataSourceAdapter) service;
			}
		}
		
		return adapter;
	}
	
	public static void register(AdapterRegister<? extends Enum<?>> register) {
		adapters.add(register);
	}
	
	public static void init() {
		for (AdapterRegister<? extends Enum<?>> adapter : adapters) {
			if (adapter.clazz == LOCALE_FILE_TYPE.class) {
				localeFileAdapter.put((LOCALE_FILE_TYPE)adapter.getType(), adapter);
			} else if (adapter.clazz == SQL_TYPE.class) {
				sqlAdapter.put((SQL_TYPE)adapter.getType(),  adapter);
			} else if (adapter.clazz == NOSQL_TYPE.class) {
				noSqlAdapter.put((NOSQL_TYPE)adapter.getType(), adapter);
			} else if (adapter.clazz == STREAM_TYPE.class) {
				streamAdapter.put((STREAM_TYPE)adapter.getType(), adapter);
			} else if (adapter.clazz == HTTP_TYPE.class) {
				httpAdapter.put((HTTP_TYPE)adapter.getType(), adapter);
			} else if (adapter.clazz == TCP_TYPE.class) {
				tcpAdapter.put((TCP_TYPE)adapter.getType(), adapter);
			}
		}
	}
}
