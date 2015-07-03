package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Getter;
import lombok.Setter;

// [NOTE] :: Lombok's @EqualsAndHashCode wasn't working with array values ...
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * {@link Element}s are used to maintain a sorted, linked-list of positions
 * within {@link IState}s.  They are sorted to simplify and optimize various
 * operations on the positions (like subsumption and merging-in new positions).
 * @param <Type> Kind of this linked-list's values.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Getter
@Setter
public class Element<Type> {

  /**
   * Reference to the previous node in this linked-list.  The previous node may
   * be null if this is the head of the list.
   * -- GETTER --
   * Reference to the previous node in this linked-list.  The previous node may
   * be null if this is the head of the list.
   * @return Reference to the previous node in this linked-list.
   * -- SETTER --
   * Reference to the previous node in this linked-list.  The previous node may
   * be null if this is the head of the list.
   * @param prev Reference to the previous node in this linked-list.
   * @return This {@link Element} for fluency.
   */
  Element<Type> prev;

  /**
   * Reference to the next node in this linked-list.  The next node may be null
   * if this is the tail of the list.
   * -- GETTER --
   * Reference to the next node in this linked-list.  The next node may be null
   * if this is the tail of the list.
   * @return Reference to the next node in this linked-list.
   * -- SETTER --
   * Reference to the next node in this linked-list.  The next node may be null
   * if this is the tail of the list.
   * @param next Reference to the next node in this linked-list.
   * @return This {@link Element} for fluency.
   */
  Element<Type> next;

  /**
   * Value of this linked-list node.
   * -- GETTER --
   * Value of this linked-list node.
   * @return Value of this linked-list node.
   * -- SETTER --
   * Value of this linked-list node.
   * @param value Value of this linked-list node.
   * @return This {@link Element} for fluency.
   */
  Type value;

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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
