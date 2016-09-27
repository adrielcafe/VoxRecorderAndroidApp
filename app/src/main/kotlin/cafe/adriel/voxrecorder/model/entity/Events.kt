package cafe.adriel.voxrecorder.model.entity

class RecordingAddedEvent(val recording: Recording)
class RecordingDeletedEvent(val recording: Recording)
class RecordingErrorEvent(val errorResId: Int)