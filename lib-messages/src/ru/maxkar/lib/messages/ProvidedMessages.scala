package ru.maxkar.lib.messages

/**
 * Implementation of messages which uses a given
 * format provider.
 * @param prefix group prefix.
 * @param provider data provider.
 */
private[messages] final class ProvidedMessages(
      prefix : String,
      provider : String ⇒  String)
    extends Messages {

  def selectDynamic(sub : String) : Messages =
    new ProvidedMessages(prefix + sub + ".", provider)

  def applyDynamic(key : String)(args : Any*) : String =
    String.format(
      provider(prefix + key),
      args.map(_.asInstanceOf[AnyRef]):_*)
}
