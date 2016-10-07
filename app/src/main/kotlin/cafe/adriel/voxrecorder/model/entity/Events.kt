package cafe.adriel.voxrecorder.model.entity

class LoadRecordingsEvent()
class SaveRecordingEvent(val recording: Recording)
class RecordingAddedEvent(val recording: Recording)
class RecordingDeletedEvent(val recording: Recording)
class RecordingErrorEvent(val errorResId: Int)