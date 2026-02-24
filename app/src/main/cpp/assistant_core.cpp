#include <jni.h>
#include <algorithm>
#include <string>

namespace {

std::string normalize(std::string text) {
    std::transform(text.begin(), text.end(), text.begin(), [](unsigned char c) {
        return static_cast<char>(std::tolower(c));
    });
    return text;
}

std::pair<std::string, float> detectAction(const std::string& spokenText) {
    const std::string normalized = normalize(spokenText);

    if (normalized.find("notification") != std::string::npos) {
        return {"OPEN_NOTIFICATIONS", 0.91F};
    }
    if (normalized.find("quick") != std::string::npos || normalized.find("wifi") != std::string::npos) {
        return {"OPEN_QUICK_SETTINGS", 0.86F};
    }
    if (normalized.find("home") != std::string::npos) {
        return {"GO_HOME", 0.88F};
    }
    if (normalized.find("settings") != std::string::npos) {
        return {"OPEN_SETTINGS", 0.84F};
    }

    return {"OPEN_SETTINGS", 0.40F};
}

}  // namespace

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_smartassistant_NativeCommandEngine_parseCommand(
    JNIEnv* env,
    jobject /* this */,
    jstring spoken_text
) {
    const char* inputChars = env->GetStringUTFChars(spoken_text, nullptr);
    const std::string spokenText = inputChars == nullptr ? "" : inputChars;
    if (inputChars != nullptr) {
        env->ReleaseStringUTFChars(spoken_text, inputChars);
    }

    const auto [action, confidence] = detectAction(spokenText);

    jclass parsedCommandClass = env->FindClass("com/example/smartassistant/ParsedCommand");
    jmethodID ctor = env->GetMethodID(parsedCommandClass, "<init>", "(Ljava/lang/String;F)V");
    jstring actionString = env->NewStringUTF(action.c_str());

    jobject result = env->NewObject(parsedCommandClass, ctor, actionString, confidence);
    env->DeleteLocalRef(actionString);
    return result;
}
