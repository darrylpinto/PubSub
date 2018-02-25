# PubSub

This is Publisher-Subscriber system.

Here, susbscribers can subcribe to a topic and get events regarding subscribed topics.
The subscriber can get an event even if it is offline. It will receive all the missed topics and the missed events on coming back online.

The subscriber only gets the events of the topic that it is subscribed to and will not get the events of the topic that it has not subscribed to.
The subcriber has the option to unsubscribe from list of subscribed topics.

Publishers can create events of an already-existing topic and publish these events.
If a topic is not there, the publisher can advertise a new topic to all the users.

The Event Manager is the Broker. It is responsible for handling all the communication in the Pub-Sub System.
