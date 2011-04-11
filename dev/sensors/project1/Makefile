COMPONENT=MessageTestAppC

BUILD_EXTRA_DEPS += SensorMessage.class

CLEAN_EXTRA = *.class SensorMessage.java
SensorMessage.class: SensorMessage.java
	javac SensorMessage.java

SensorMessage.java:
	mig java -target=null -java-classname=SensorMessage SensorMessage.h SensorMessage -o $@

include $(MAKERULES)
