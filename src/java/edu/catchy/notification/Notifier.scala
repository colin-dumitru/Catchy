package edu.catchy.notification


/**
 * Catalin Dumitru
 * Date: 6/15/13
 * Time: 10:21 PM
 */
object Notifier {
  private lazy val bridge: NotifierBridge = new NotifierBridge()

  def displayNotification(title: String, content: String) {
    bridge.displayMessage(title, content)
  }

}
