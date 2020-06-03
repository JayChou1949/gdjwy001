package com.upd.hwcloud.common.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JsonCutNull extends ObjectMapper {
	
	public JsonCutNull() {
		super();
		
 
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(
				new JsonSerializer<Object>() {

					@Override
					public void serialize(Object value, JsonGenerator jgen,
							SerializerProvider provider) throws IOException,
                            JsonProcessingException {
						if(value instanceof Number){
							jgen.writeNumber(0);
						}
						else {
							jgen.writeString("");
						}
						
					}
				});
	}

}