# Steganography Tool

A Java application that uses Least Significant Bit (LSB) steganography to hide and extract text messages within images.

## Features

* Encode text messages into image files.
* Decode and extract hidden messages from images.
* Graphical user interface for easy interaction.
* Support for PNG images to ensure lossless data storage.

## Requirements

* Java Development Kit (JDK) 17 or higher.
* Apache Maven for building the project.

## Installation

1. Clone the repository to your local machine.
2. Navigate to the project directory.

## Building the Project

Use Maven to compile the source code and package it into a JAR file:

```bash
mvn clean package
```

The resulting JAR file will be located in the target directory.

## Usage

To run the application, execute the following command:

```bash
java -jar target/steganography-lsb-1.0-SNAPSHOT.jar
```

### Encoding a Message

1. Select the "Select Cover Image" button in the Encode panel.
2. Enter the text message you wish to hide.
3. Click "Hide & Save" and choose a destination for the new image.

### Decoding a Message

1. Select the "Select Stego Image" button in the Decode panel.
2. Click "Decode Message" to view the hidden text.

## How it Works

This tool utilizes the Least Significant Bit (LSB) method. It modifies the last bit of each color channel (Red, Green, and Blue) in every pixel to store binary data from the text message. Because only the least significant bit is changed, the visual difference is imperceptible to the human eye.
