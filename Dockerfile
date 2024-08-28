FROM openjdk:17-jdk-slim

# Install FFmpeg
RUN apt-get update && apt-get install -y ffmpeg

# Set up the working directory
WORKDIR /app

# Ensure directories exist
RUN mkdir -p uploadedFiles processedFiles

# Copy application files
COPY . /app

# Run the application
CMD ["java", "-jar", "your-application.jar"]
