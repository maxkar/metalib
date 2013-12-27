package ru.maxkar.lib.messages

import scala.language.dynamics

import java.util.Locale
import java.util.ResourceBundle
import java.util.MissingResourceException

/**
 * Specialization of dynamic for the localization messages.
 * <p> Primary aim is to provide "fluent" interface. For example,
 * msg.errors.incomatibleTypes() or msg.geetUser(firstName, secondName).
 * <p> This library does not have support for any named arguments.
 * This is priarily due to underlying platform restrictions (java
 * String.format bundle).
 */
trait Messages extends Dynamic {
  /**
   * Provides a subsection of this messages.
   * Binding for static subgroup accessor for code like
   * <pre>val errors = msg.errors</pre>
   * @param sub sub-group name.
   * @return resulting messages group.
   */
  def selectDynamic(sub : String) : Messages

  /**
   * Formats a message with a given arguments.
   * Accessor for messages like <pre>msg.greet(name)</pre>
   * @param key message key.
   * @param actual arguments.
   * @return formatted message.
   */
  def applyDynamic(key : String)(args : Any*) : String
}

/**
 * General accessor for the messages.
 */
object Messages {
  /**
   * Creates a new messages using given format string
   * provider. This formatter must provider String.format-like
   * format strings.
   * <p>Sub-groups use dot ('.') as a group separator. So member
   * x of group y will have a key "x.y". So format should not care
   * about any [sub]groups.
   * @param provider format string provider for the given keys.
   * @return messages using a given provider.
   */
  def fromFormatProvider(
      provider : String ⇒ String) : Messages =
    new ProvidedMessages("", provider)


  /**
   * Creates a new messages using a message bundle with a specified
   * locale. This formatter never fails on absent keys.
   * @param bundle resource bundle.
   * @return messages which use bundle as a message format provider.
   */
  def fromMessageBundle(bundle : ResourceBundle) : Messages =
    fromFormatProvider(key ⇒
      try {
        bundle.getString(key)
      } catch {
        case e : MissingResourceException ⇒
          '!' + key + '!'
      })

  /**
   * Creates a new messages using a message bundle with a specified
   * locale. This formatter never fails on absent keys.
   * @param bundle message bundle name.
   * @param locale locale to get a message bundle for.
   * @return messages which use bundle as a message format provider.
   */
  def fromMessageBundle(bundle : String, locale : Locale) : Messages =
    fromMessageBundle(ResourceBundle.getBundle(bundle, locale))


  /**
   * Creates a new messages using a message bundle with a default
   * locale. This formatter never fails on absent keys.
   * @param bundle message bundle name.
   * @param locale locale to get a message bundle for.
   * @return messages which use bundle as a message format provider.
   */
  def fromMessageBundle(bundle : String) : Messages =
    fromMessageBundle(ResourceBundle.getBundle(bundle))
}
