package cafe.adriel.voxrecorder.model.entity

class LoadRecordingsEvent()
class RecordingAddedEvent(val recording: Recording)
class RecordingDeletedEvent(val recording: Recording)
class RecordingErrorEvent(val errorResId: Int)