package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Getter;
import lombok.Setter;

// [NOTE] :: Lombok's @EqualsAndHashCode wasn't working with array values ...
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class Element<Type> {
  Element<Type> prev;
  Element<Type> next;
  Type value;

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  @Override
  public boolean equals(final Object object) {
  	if (null == object) return false;
  	if (this == object) return true;
  	if ( ! (object instanceof Element)) return false;

  	Element<?> lhs = this;
  	while (null != lhs.prev()) {
  		lhs = lhs.prev();
  	}

  	Element<?> rhs = (Element<?>) object;
  	while (null != rhs.prev()) {
  		rhs = rhs.prev();
  	}

  	while (rhs != null && lhs != null) {
  		if ( ! EqualsBuilder.reflectionEquals(rhs.value(), lhs.value())) {
  			return false;
  		}

  		rhs = rhs.next();
  		lhs = lhs.next();
  	}

  	return rhs == null && lhs == null;
  }

  @Override
  public int hashCode() {
  	int i = 0;
  	Element<Type> curr = this;

  	while (null != curr.prev()) {
  		i += 1; // keep up with the current node's index
  		curr = curr.prev();
  	}

  	final HashCodeBuilder builder = new HashCodeBuilder(827809, 5954297);

  	while (null != curr) {
  		builder.append(curr.value());
  		curr = curr.next();
  	}

		builder.append(i);
		return builder.toHashCode();
  }
}
