package com.pisces.core.converter;

import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.utils.AppUtils;
import com.pisces.core.utils.EntityUtils;

public class EntitySerializerModifier extends BeanSerializerModifier {
	
	public static class UserFieldWriter extends BeanPropertyWriter {
		private static final long serialVersionUID = 5358862065792784550L;
		
		@Override
		public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
			EntityObject entity = (EntityObject)bean;
			List<Property> properties = AppUtils.getPropertyService().get(entity.getClass());
			for (Property property : properties) {
				if (!property.getInherent()) {
					gen.writeFieldName(property.getCode());
					gen.writeString(EntityUtils.getTextValue(entity, property));
				}
			}
		}
		
		@Override
		public void fixAccess(SerializationConfig config) {
		}
		
		@Override
		public boolean hasSerializer() {
			return true;
		}
	}
	
	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		if (EntityObject.class.isAssignableFrom(beanDesc.getBeanClass())) {
			beanProperties.add(new UserFieldWriter());
		}
		return super.changeProperties(config, beanDesc, beanProperties);
	}
}
